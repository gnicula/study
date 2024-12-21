# backend/services/draft_analyzer.py
from typing import List, Tuple, Dict
import json
from models import Hero, HeroRecommendation

class DraftAnalyzer:
    def __init__(self):
        # In production, this would load from a database
        self.hero_data = self._load_hero_data()
        self.synergy_matrix = self._load_synergy_matrix()
        self.counter_matrix = self._load_counter_matrix()

    def _load_hero_data(self) -> Dict[int, Hero]:
        # Mock hero data - in production would load from DB
        return {
            1: Hero(id=1, name="Anti-Mage", roles=["Carry"], base_win_rate=0.52),
            2: Hero(id=2, name="Axe", roles=["Initiator", "Durable"], base_win_rate=0.54),
            # Add more heroes...
        }

    def _load_synergy_matrix(self) -> Dict[Tuple[int, int], float]:
        # Mock synergy data - in production would load from DB
        return {
            (1, 2): 0.6,  # Synergy score between hero 1 and 2
            # Add more synergy scores...
        }

    def _load_counter_matrix(self) -> Dict[Tuple[int, int], float]:
        # Mock counter data - in production would load from DB
        return {
            (1, 3): 0.7,  # How well hero 1 counters hero 3
            # Add more counter scores...
        }

    def calculate_synergy_score(self, hero_id: int, ally_heroes: List[int]) -> float:
        if not ally_heroes:
            return 0.5
        
        total_score = 0.0
        for ally_id in ally_heroes:
            pair = tuple(sorted([hero_id, ally_id]))
            score = self.synergy_matrix.get(pair, 0.5)
            total_score += score
        
        return total_score / len(ally_heroes)

    def calculate_counter_score(self, hero_id: int, enemy_heroes: List[int]) -> float:
        if not enemy_heroes:
            return 0.5
        
        total_score = 0.0
        for enemy_id in enemy_heroes:
            score = self.counter_matrix.get((hero_id, enemy_id), 0.5)
            total_score += score
        
        return total_score / len(enemy_heroes)

    def get_recommendation(self, ally_heroes: List[int], enemy_heroes: List[int], stage: int) -> HeroRecommendation:
        best_hero = None
        best_score = -1
        
        for hero_id, hero_data in self.hero_data.items():
            if hero_id in ally_heroes or hero_id in enemy_heroes:
                continue
                
            synergy_score = self.calculate_synergy_score(hero_id, ally_heroes)
            counter_score = self.calculate_counter_score(hero_id, enemy_heroes)
            
            # Weight the scores based on the draft stage
            stage_weight = min(stage / 10, 1.0)
            combined_score = (synergy_score * (1 - stage_weight) + 
                            counter_score * stage_weight)
            
            if combined_score > best_score:
                best_score = combined_score
                best_hero = hero_id
        
        if best_hero is None:
            raise ValueError("No valid hero recommendations found")
            
        hero = self.hero_data[best_hero]
        explanation = self._generate_explanation(best_hero, ally_heroes, enemy_heroes)
        
        return HeroRecommendation(
            hero_id=best_hero,
            name=hero.name,
            confidence=best_score,
            synergy_score=self.calculate_synergy_score(best_hero, ally_heroes),
            counter_score=self.calculate_counter_score(best_hero, enemy_heroes),
            explanation=explanation,
            win_rate=hero.base_win_rate
        )

    def _generate_explanation(self, hero_id: int, ally_heroes: List[int], enemy_heroes: List[int]) -> str:
        # In production, this would use more sophisticated NLP
        hero = self.hero_data[hero_id]
        
        synergies = [self.hero_data[h].name for h in ally_heroes 
                    if self.calculate_synergy_score(hero_id, [h]) > 0.6]
        counters = [self.hero_data[h].name for h in enemy_heroes 
                   if self.calculate_counter_score(hero_id, [h]) > 0.6]
        
        explanation = f"{hero.name} would be a strong pick because "
        
        if synergies:
            explanation += f"it synergizes well with {', '.join(synergies)}. "
        if counters:
            explanation += f"It also counters {', '.join(counters)}. "
            
        explanation += f"As a {', '.join(hero.roles)}, it fills an important role in the team composition."
        
        return explanation
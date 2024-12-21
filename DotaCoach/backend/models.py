# backend/models.py
from pydantic import BaseModel, Field
from typing import List, Optional
from enum import Enum

class HeroRole(str, Enum):
    CARRY = "Carry"
    SUPPORT = "Support"
    INITIATOR = "Initiator"
    DURABLE = "Durable"
    NUKER = "Nuker"
    DISABLER = "Disabler"
    JUNGLER = "Jungler"
    ESCAPE = "Escape"
    PUSHER = "Pusher"

class Hero(BaseModel):
    id: int
    name: str
    roles: List[HeroRole]
    base_win_rate: float = Field(..., ge=0.0, le=1.0)
    image_url: Optional[str] = None

class DraftAnalysisRequest(BaseModel):
    ally_heroes: List[int] = Field(..., min_items=1, max_items=5)
    enemy_heroes: List[int] = Field(..., min_items=0, max_items=5)
    stage: int = Field(..., ge=1, le=10)

class HeroRecommendation(BaseModel):
    hero_id: int
    name: str
    confidence: float = Field(..., ge=0.0, le=1.0)
    synergy_score: float = Field(..., ge=0.0, le=1.0)
    counter_score: float = Field(..., ge=0.0, le=1.0)
    explanation: str
    win_rate: float = Field(..., ge=0.0, le=1.0)

class ItemBuild(BaseModel):
    early_game: List[str]
    mid_game: List[str]
    late_game: List[str]
    situational: List[str]

class AbilityBuild(BaseModel):
    skill_order: List[str]
    talent_choices: List[str]

class BuildRecommendation(BaseModel):
    items: ItemBuild
    abilities: AbilityBuild
    explanation: str

class ImageAnalysisResponse(BaseModel):
    detected_heroes: dict[str, List[int]]
    draft_stage: int
    confidence_score: float = Field(..., ge=0.0, le=1.0)
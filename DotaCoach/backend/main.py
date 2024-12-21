from fastapi import FastAPI, File, UploadFile, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from typing import List, Optional
import httpx
import os
import json

app = FastAPI()

# Enable CORS
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:3000"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Models
class DraftAnalysis(BaseModel):
    ally_heroes: List[int]
    enemy_heroes: List[int]
    stage: int

class HeroRecommendation(BaseModel):
    hero_id: int
    name: str
    confidence: float
    synergy_score: float
    counter_score: float
    explanation: str
    win_rate: float

class BuildRecommendation(BaseModel):
    items: List[str]
    abilities: List[str]
    explanation: str

# Mock hero data - in production, this would come from a database
HERO_DATA = {
    1: {"name": "Anti-Mage", "roles": ["Carry"], "base_win_rate": 0.52},
    2: {"name": "Axe", "roles": ["Initiator", "Durable"], "base_win_rate": 0.54},
    # Add more heroes...
}

@app.post("/analyze-draft", response_model=HeroRecommendation)
async def analyze_draft(draft: DraftAnalysis):
    """Analyze the current draft and recommend heroes"""
    try:
        # Mock analysis logic - in production, this would use real data and ML models
        recommended_hero = {
            "hero_id": 1,
            "name": "Anti-Mage",
            "confidence": 0.85,
            "synergy_score": 0.78,
            "counter_score": 0.82,
            "explanation": "Anti-Mage would be strong against the enemy's magic-heavy lineup...",
            "win_rate": 0.52
        }
        return recommended_hero
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.post("/analyze-image")
async def analyze_image(file: UploadFile = File(...)):
    """Analyze uploaded draft image using a multimodal LLM"""
    try:
        # In production, this would use actual image analysis
        # For prototype, return mock data
        contents = await file.read()
        return {
            "detected_heroes": {
                "allies": [1, 2, 3],
                "enemies": [4, 5, 6]
            },
            "draft_stage": 2
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/build-recommendation/{hero_id}")
async def get_build_recommendation(hero_id: int):
    """Get build recommendations for a specific hero"""
    try:
        # Mock build recommendation - in production, this would use real data
        return {
            "items": ["Power Treads", "Battle Fury", "Manta Style"],
            "abilities": ["Blink", "Mana Break", "Spell Shield"],
            "explanation": "This build focuses on farm acceleration and split pushing..."
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/hero-stats/{hero_id}")
async def get_hero_stats(hero_id: int):
    """Get detailed hero statistics"""
    try:
        # In production, this would fetch from OpenDota API
        async with httpx.AsyncClient() as client:
            # Mock API call
            return HERO_DATA.get(hero_id, {})
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
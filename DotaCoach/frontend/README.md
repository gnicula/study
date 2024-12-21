# Dota 2 Draft Coach 🎮

A modern web application that helps Dota 2 players make informed decisions during the drafting phase. Using image analysis and advanced algorithms, this tool provides real-time hero recommendations and strategic insights.

## Features ✨

- **Image Upload and Analysis**: Upload screenshots of your draft phase for instant analysis
- **Hero Recommendations**: Get personalized hero suggestions based on your team composition and enemy picks
- **Synergy Analysis**: Understand how well heroes work together with detailed synergy scores
- **Counter Picks**: Identify strong counter picks against the enemy team
- **Build Recommendations**: Get situational item and ability builds
- **Modern UI**: Clean, responsive interface with real-time updates

## Tech Stack 🛠️

### Backend
- FastAPI
- Python 3.9+
- Pydantic for data validation
- Image processing capabilities
- Mock data system (ready for real API integration)

### Frontend
- React 18
- Tailwind CSS
- Shadcn/UI components
- Lucide icons
- Modern file upload system

## Setup 🚀

### Prerequisites
- Python 3.9 or higher
- Node.js 16 or higher
- npm or yarn

### Backend Setup

1. Create and activate virtual environment:
```bash
python -m venv venv
source venv/bin/activate  # On Windows: venv\Scripts\activate
```

2. Install dependencies:
```bash
cd backend
pip install -r requirements.txt
```

3. Run the server:
```bash
uvicorn main:app --reload
```

The backend will be available at `http://localhost:8000`

### Frontend Setup

1. Install dependencies:
```bash
cd frontend
npm install
```

2. Start development server:
```bash
npm run dev
```

The frontend will be available at `http://localhost:3000`

## API Documentation 📚

### Main Endpoints

#### POST `/analyze-image`
Upload and analyze draft screen image
- Input: Form data with image file
- Output: Detected heroes and draft stage

#### POST `/analyze-draft`
Get hero recommendations based on current draft
- Input: Current picks and draft stage
- Output: Hero recommendation with detailed analysis

#### GET `/build-recommendation/{hero_id}`
Get build recommendations for specific hero
- Input: Hero ID
- Output: Item and ability recommendations

## Project Structure 📁

```
dota-draft-coach/
├── backend/
│   ├── main.py              # FastAPI application
│   ├── models.py            # Pydantic models
│   ├── requirements.txt     # Python dependencies
│   └── services/           
│       ├── draft_analyzer.py
│       └── image_analyzer.py
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   │   └── DotaDraftCoach.jsx
│   │   ├── App.jsx
│   │   └── main.jsx
│   └── package.json
│
└── README.md
```

## Development Status 🚧

This project is currently in prototype phase. Future improvements planned:

- [ ] Integration with OpenDota API for real data
- [ ] Machine learning model for draft analysis
- [ ] User authentication system
- [ ] Match history analysis
- [ ] Professional match statistics
- [ ] Advanced filtering options

## Contributing 🤝

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License 📝

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments 👏

- OpenDota API for Dota 2 data
- Valve for Dota 2
- Shadcn/UI for component library

## Contact 📧

Project Link: [https://github.com/yourusername/dota-draft-coach](https://github.com/yourusername/dota-draft-coach)

---

*Note: This is a prototype version. Some features may use mock data until integrated with real APIs.*
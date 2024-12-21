import React, { useState } from 'react';
import { Camera, Upload, Book, Clock } from 'lucide-react';
import { Card, CardHeader, CardTitle, CardContent } from '@/components/ui/card';
import { Alert, AlertDescription } from '@/components/ui/alert';

const DotaDraftCoach = () => {
  const [selectedFile, setSelectedFile] = useState(null);
  const [analysis, setAnalysis] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleFileUpload = async (event) => {
    const file = event.target.files[0];
    setSelectedFile(file);
    
    if (file) {
      setLoading(true);
      setError(null);
      
      const formData = new FormData();
      formData.append('file', file);
      
      try {
        const response = await fetch('http://localhost:8000/analyze-image', {
          method: 'POST',
          body: formData,
        });
        
        if (!response.ok) throw new Error('Failed to analyze image');
        
        const imageAnalysis = await response.json();
        const draftAnalysis = await fetch('http://localhost:8000/analyze-draft', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            ally_heroes: imageAnalysis.detected_heroes.allies,
            enemy_heroes: imageAnalysis.detected_heroes.enemies,
            stage: imageAnalysis.draft_stage,
          }),
        });
        
        if (!draftAnalysis.ok) throw new Error('Failed to analyze draft');
        
        const recommendation = await draftAnalysis.json();
        setAnalysis(recommendation);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 p-8">
      <div className="max-w-4xl mx-auto space-y-8">
        <header className="text-center">
          <h1 className="text-4xl font-bold text-gray-900 mb-4">Dota 2 Draft Coach</h1>
          <p className="text-gray-600">Upload your draft screen for instant analysis and recommendations</p>
        </header>

        <Card className="bg-white shadow-lg">
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <Upload className="w-6 h-6" />
              Upload Draft Screen
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="flex items-center justify-center w-full">
              <label className="flex flex-col items-center justify-center w-full h-64 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 hover:bg-gray-100">
                <div className="flex flex-col items-center justify-center pt-5 pb-6">
                  <Camera className="w-12 h-12 mb-4 text-gray-400" />
                  <p className="mb-2 text-sm text-gray-500">
                    <span className="font-semibold">Click to upload</span> or drag and drop
                  </p>
                  <p className="text-xs text-gray-500">PNG, JPG or JPEG (MAX. 800x400px)</p>
                </div>
                <input
                  type="file"
                  className="hidden"
                  accept="image/*"
                  onChange={handleFileUpload}
                />
              </label>
            </div>
          </CardContent>
        </Card>

        {loading && (
          <Card className="animate-pulse">
            <CardHeader>
              <CardTitle className="flex items-center gap-2">
                <Clock className="w-6 h-6" />
                Analyzing Draft...
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="h-32 bg-gray-200 rounded"></div>
            </CardContent>
          </Card>
        )}

        {error && (
          <Alert variant="destructive">
            <AlertDescription>{error}</AlertDescription>
          </Alert>
        )}

        {analysis && (
          <Card>
            <CardHeader>
              <CardTitle className="flex items-center gap-2">
                <Book className="w-6 h-6" />
                Draft Analysis
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="space-y-6">
                <div>
                  <h3 className="text-lg font-semibold mb-2">Recommended Hero</h3>
                  <p className="text-2xl font-bold text-blue-600">{analysis.name}</p>
                  <div className="mt-2 grid grid-cols-3 gap-4">
                    <div className="text-center p-4 bg-gray-50 rounded">
                      <p className="text-sm text-gray-500">Confidence</p>
                      <p className="text-lg font-semibold">{(analysis.confidence * 100).toFixed(1)}%</p>
                    </div>
                    <div className="text-center p-4 bg-gray-50 rounded">
                      <p className="text-sm text-gray-500">Synergy</p>
                      <p className="text-lg font-semibold">{(analysis.synergy_score * 100).toFixed(1)}%</p>
                    </div>
                    <div className="text-center p-4 bg-gray-50 rounded">
                      <p className="text-sm text-gray-500">Counter Score</p>
                      <p className="text-lg font-semibold">{(analysis.counter_score * 100).toFixed(1)}%</p>
                    </div>
                  </div>
                </div>
                
                <div>
                  <h3 className="text-lg font-semibold mb-2">Analysis</h3>
                  <p className="text-gray-700">{analysis.explanation}</p>
                </div>

                <div>
                  <h3 className="text-lg font-semibold mb-2">Win Rate</h3>
                  <div className="w-full bg-gray-200 rounded-full h-2.5">
                    <div
                      className="bg-blue-600 h-2.5 rounded-full"
                      style={{ width: `${analysis.win_rate * 100}%` }}
                    ></div>
                  </div>
                  <p className="text-sm text-gray-500 mt-1">
                    {(analysis.win_rate * 100).toFixed(1)}% overall win rate
                  </p>
                </div>
              </div>
            </CardContent>
          </Card>
        )}
      </div>
    </div>
  );
};

export default DotaDraftCoach;
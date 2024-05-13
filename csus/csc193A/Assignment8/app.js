'use strict';
const express = require('express');
const app = express();


// define all endpoints here

// Excercise 1
app.get('/math/circle/:r', (req, res) => {
  const radius = parseFloat(req.params.r);

  if (isNaN(radius) || radius <= 0) {
    return res.status(400).json({ error: 'Invalid radius provided' });
  }

  const area = Math.PI * radius * radius;
  const circumference = Math.PI * 2 * radius;

  res.json({ area, circumference });
});

// Excercise 2
app.get('/hello/name', (req, res) => {
  const { first, last } = req.query;

  // Check if both first and last parameters are provided
  if (!first || !last) {
    let errorMessage = '';
    if (!first) {
      errorMessage += 'Missing Required GET parameter: first';
    }
    if (!last) {
      errorMessage += errorMessage ? ', last' : 'Missing Required GET parameter: last';
    }
    return res.status(400).send(errorMessage);
  }

  const greeting = `Hello ${first} ${last}`;
  res.send(greeting);
});

app.use(express.static('public'));
const PORT = process.env.PORT || 8000;
app.listen(PORT);

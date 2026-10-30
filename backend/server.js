require('dotenv').config();
const express = require('express');
const cors = require('cors');
const helmet = require('helmet');
const rateLimit = require('express-rate-limit');
const path = require('path');
const errorHandler = require('./middleware/errorHandler');
const pool = require('./config/database');

const app = express();
const PORT = process.env.PORT || 3000;

app.use(helmet({ contentSecurityPolicy: false }));
app.use(cors({ origin: process.env.CORS_ORIGIN || '*' }));
app.use(express.json());

const authLimiter = rateLimit({
  windowMs: 15 * 60 * 1000,
  max: 100,
  message: { success: false, message: 'Too many requests, please try again later.' }
});

const apiLimiter = rateLimit({
  windowMs: 15 * 60 * 1000,
  max: 1000,
  message: { success: false, message: 'Too many requests, please try again later.' }
});

app.use('/api/auth', authLimiter);
app.use('/api', apiLimiter);

// Mount routes
app.use('/api/auth', require('./routes/auth'));
app.use('/api/states', require('./routes/states'));
app.use('/api/heritage', require('./routes/heritage'));
app.use('/api/festivals', require('./routes/festivals'));
app.use('/api/foods', require('./routes/foods'));
app.use('/api/traditions', require('./routes/traditions'));
app.use('/api/dances', require('./routes/dances'));
app.use('/api/music', require('./routes/music'));
app.use('/api/artsCrafts', require('./routes/artsCrafts'));
app.use('/api/stories', require('./routes/stories'));
app.use('/api/search', require('./routes/search'));
app.use('/api/favorites', require('./routes/favorites'));
app.use('/api/collections', require('./routes/collections'));
app.use('/api/contributions', require('./routes/contributions'));
app.use('/api/gallery', require('./routes/gallery'));
app.use('/api/timeline', require('./routes/timeline'));
app.use('/api/museum', require('./routes/museum'));
app.use('/api/audio', require('./routes/audio'));
app.use('/api/calendar', require('./routes/calendar'));
app.use('/api/heritageRisk', require('./routes/heritageRisk'));
app.use('/api/newsletter', require('./routes/newsletter'));
app.use('/api/admin', require('./routes/admin'));
app.use('/api/dresses', require('./routes/dresses'));
app.use('/api/languages', require('./routes/languages'));
app.use('/api/compare', require('./routes/compare'));

// Serve frontend static files
app.use(express.static(path.join(__dirname, '../frontend')));
app.get('*', (req, res) => {
  res.sendFile(path.join(__dirname, '../frontend/index.html'));
});

app.use(errorHandler);

app.listen(PORT, async () => {
  console.log(`Server running on port ${PORT}`);
  try {
    const connection = await pool.getConnection();
    console.log('Database connected successfully');
    connection.release();
  } catch (err) {
    console.error('Database connection failed:', err.message);
  }
});

const express = require('express');
const router = express.Router();
const pool = require('../config/database');
const { authenticate } = require('../middleware/auth');

router.post('/', authenticate, async (req, res, next) => {
  try {
    const { title, description, category, state_id } = req.body;
    if (!title || !description || !category || !state_id) {
      return res.status(400).json({ success: false, message: 'Title, description, category, and state_id are required' });
    }

    const [result] = await pool.query(
      'INSERT INTO contributions (user_id, title, description, category, state_id, status) VALUES (?, ?, ?, ?, ?, ?)',
      [req.user.id, title, description, category, state_id, 'pending']
    );

    res.status(201).json({ success: true, message: 'Contribution submitted successfully', id: result.insertId });
  } catch (error) { next(error); }
});

router.get('/my', authenticate, async (req, res, next) => {
  try {
    const [rows] = await pool.query('SELECT * FROM contributions WHERE user_id = ? ORDER BY created_at DESC', [req.user.id]);
    res.json({ success: true, data: rows });
  } catch (error) { next(error); }
});

module.exports = router;

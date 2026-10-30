const express = require('express');
const router = express.Router();
const pool = require('../config/database');
const { authenticate } = require('../middleware/auth');

router.get('/', authenticate, async (req, res, next) => {
  try {
    const [rows] = await pool.query('SELECT * FROM favorites WHERE user_id = ? ORDER BY created_at DESC', [req.user.id]);
    res.json({ success: true, data: rows });
  } catch (error) { next(error); }
});

router.post('/', authenticate, async (req, res, next) => {
  try {
    const { item_type, item_id } = req.body;
    if (!item_type || !item_id) {
      return res.status(400).json({ success: false, message: 'item_type and item_id required' });
    }

    const [existing] = await pool.query('SELECT id FROM favorites WHERE user_id = ? AND item_type = ? AND item_id = ?', [req.user.id, item_type, item_id]);
    if (existing.length > 0) {
      return res.status(400).json({ success: false, message: 'Already in favorites' });
    }

    const [result] = await pool.query('INSERT INTO favorites (user_id, item_type, item_id) VALUES (?, ?, ?)', [req.user.id, item_type, item_id]);
    res.status(201).json({ success: true, data: { id: result.insertId, item_type, item_id } });
  } catch (error) { next(error); }
});

router.delete('/:id', authenticate, async (req, res, next) => {
  try {
    const [result] = await pool.query('DELETE FROM favorites WHERE id = ? AND user_id = ?', [req.params.id, req.user.id]);
    if (result.affectedRows === 0) {
      return res.status(404).json({ success: false, message: 'Favorite not found or not owned by user' });
    }
    res.json({ success: true, message: 'Removed from favorites' });
  } catch (error) { next(error); }
});

module.exports = router;

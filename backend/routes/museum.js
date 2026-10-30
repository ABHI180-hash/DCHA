const express = require('express');
const router = express.Router();
const pool = require('../config/database');

router.get('/', async (req, res, next) => {
  try {
    const { state_id, section, category, search, page = 1, limit = 50 } = req.query;
    const offset = (page - 1) * limit;

    let query = 'SELECT * FROM museum_artifacts WHERE 1=1';
    let countQuery = 'SELECT COUNT(*) as total FROM museum_artifacts WHERE 1=1';
    const params = [];

    if (state_id) { query += ' AND state_id = ?'; countQuery += ' AND state_id = ?'; params.push(state_id); }
    if (section) { query += ' AND section = ?'; countQuery += ' AND section = ?'; params.push(section); }
    if (category) { query += ' AND category = ?'; countQuery += ' AND category = ?'; params.push(category); }
    
    if (search) {
      query += ' AND (name LIKE ? OR description LIKE ?)';
      countQuery += ' AND (name LIKE ? OR description LIKE ?)';
      params.push(`%${search}%`, `%${search}%`);
    }

    query += ` ORDER BY name ASC LIMIT ? OFFSET ?`;
    
    const [rows] = await pool.query(query, [...params, parseInt(limit), parseInt(offset)]);
    const [countRows] = await pool.query(countQuery, params);

    res.json({ success: true, data: rows, total: countRows[0].total, page: parseInt(page), limit: parseInt(limit) });
  } catch (error) { next(error); }
});

router.get('/:id', async (req, res, next) => {
  try {
    const [rows] = await pool.query('SELECT * FROM museum_artifacts WHERE id = ?', [req.params.id]);
    if (rows.length === 0) return res.status(404).json({ success: false, message: 'Artifact not found' });
    res.json({ success: true, data: rows[0] });
  } catch (error) { next(error); }
});

module.exports = router;

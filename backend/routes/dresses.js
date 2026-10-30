const express = require('express');
const router = express.Router();
const pool = require('../config/database');

router.get('/', async (req, res, next) => {
  try {
    const { state_id, search, page = 1, limit = 50 } = req.query;
    const offset = (page - 1) * limit;

    let query = 'SELECT * FROM traditional_dresses WHERE 1=1';
    let countQuery = 'SELECT COUNT(*) as total FROM traditional_dresses WHERE 1=1';
    const params = [];

    if (state_id) { query += ' AND state_id = ?'; countQuery += ' AND state_id = ?'; params.push(state_id); }
    
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

module.exports = router;

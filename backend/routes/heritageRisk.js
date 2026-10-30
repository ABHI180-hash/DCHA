const express = require('express');
const router = express.Router();
const pool = require('../config/database');

router.get('/', async (req, res, next) => {
  try {
    const { state_id, risk_level, search, page = 1, limit = 50 } = req.query;
    const offset = (page - 1) * limit;

    let query = 'SELECT * FROM heritage_risks WHERE 1=1';
    let countQuery = 'SELECT COUNT(*) as total FROM heritage_risks WHERE 1=1';
    const params = [];

    if (state_id) { query += ' AND state_id = ?'; countQuery += ' AND state_id = ?'; params.push(state_id); }
    if (risk_level) { query += ' AND risk_level = ?'; countQuery += ' AND risk_level = ?'; params.push(risk_level); }
    
    if (search) {
      query += ' AND (site_name LIKE ? OR description LIKE ? OR threat LIKE ?)';
      countQuery += ' AND (site_name LIKE ? OR description LIKE ? OR threat LIKE ?)';
      params.push(`%${search}%`, `%${search}%`, `%${search}%`);
    }

    query += ` ORDER BY risk_level DESC, site_name ASC LIMIT ? OFFSET ?`;
    
    const [rows] = await pool.query(query, [...params, parseInt(limit), parseInt(offset)]);
    const [countRows] = await pool.query(countQuery, params);

    res.json({ success: true, data: rows, total: countRows[0].total, page: parseInt(page), limit: parseInt(limit) });
  } catch (error) { next(error); }
});

module.exports = router;

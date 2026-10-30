const express = require('express');
const router = express.Router();
const pool = require('../config/database');

router.get('/', async (req, res, next) => {
  try {
    const { state_id, month, search, page = 1, limit = 50 } = req.query;
    const offset = (page - 1) * limit;

    let query = 'SELECT f.*, s.name as state_name FROM festivals f LEFT JOIN states s ON f.state_id = s.id WHERE 1=1';
    let countQuery = 'SELECT COUNT(*) as total FROM festivals f LEFT JOIN states s ON f.state_id = s.id WHERE 1=1';
    const params = [];

    if (state_id) {
      query += ' AND f.state_id = ?';
      countQuery += ' AND f.state_id = ?';
      params.push(state_id);
    }
    if (month) {
      query += ' AND f.month = ?';
      countQuery += ' AND f.month = ?';
      params.push(month);
    }
    if (search) {
      query += ' AND (f.name LIKE ? OR f.description LIKE ?)';
      countQuery += ' AND (f.name LIKE ? OR f.description LIKE ?)';
      params.push(`%${search}%`, `%${search}%`);
    }

    query += ` ORDER BY f.name ASC LIMIT ? OFFSET ?`;
    
    const [rows] = await pool.query(query, [...params, parseInt(limit), parseInt(offset)]);
    const [countRows] = await pool.query(countQuery, params);

    res.json({
      success: true,
      data: rows,
      total: countRows[0].total,
      page: parseInt(page),
      limit: parseInt(limit)
    });
  } catch (error) {
    next(error);
  }
});

module.exports = router;

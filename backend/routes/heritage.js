const express = require('express');
const router = express.Router();
const pool = require('../config/database');

router.get('/', async (req, res, next) => {
  try {
    const { state_id, category, region, search, page = 1, limit = 50 } = req.query;
    const offset = (page - 1) * limit;

    let query = 'SELECT h.*, s.name as state_name FROM heritage_sites h LEFT JOIN states s ON h.state_id = s.id WHERE 1=1';
    let countQuery = 'SELECT COUNT(*) as total FROM heritage_sites h LEFT JOIN states s ON h.state_id = s.id WHERE 1=1';
    const params = [];

    if (state_id) {
      query += ' AND h.state_id = ?';
      countQuery += ' AND h.state_id = ?';
      params.push(state_id);
    }
    if (category) {
      query += ' AND h.category = ?';
      countQuery += ' AND h.category = ?';
      params.push(category);
    }
    if (region) {
      query += ' AND s.region = ?';
      countQuery += ' AND s.region = ?';
      params.push(region);
    }
    if (search) {
      query += ' AND (h.name LIKE ? OR h.description LIKE ?)';
      countQuery += ' AND (h.name LIKE ? OR h.description LIKE ?)';
      params.push(`%${search}%`, `%${search}%`);
    }

    query += ` ORDER BY h.name ASC LIMIT ? OFFSET ?`;
    
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

router.get('/:id', async (req, res, next) => {
  try {
    const [rows] = await pool.query('SELECT h.*, s.name as state_name FROM heritage_sites h LEFT JOIN states s ON h.state_id = s.id WHERE h.id = ?', [req.params.id]);
    if (rows.length === 0) {
      return res.status(404).json({ success: false, message: 'Heritage site not found' });
    }
    res.json({ success: true, data: rows[0] });
  } catch (error) {
    next(error);
  }
});

module.exports = router;

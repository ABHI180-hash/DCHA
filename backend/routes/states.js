const express = require('express');
const router = express.Router();
const pool = require('../config/database');

router.get('/', async (req, res, next) => {
  try {
    const { region, search, sort = 'name', page = 1, limit = 50 } = req.query;
    const offset = (page - 1) * limit;

    let query = 'SELECT * FROM states WHERE 1=1';
    let countQuery = 'SELECT COUNT(*) as total FROM states WHERE 1=1';
    const params = [];

    if (region) {
      query += ' AND region = ?';
      countQuery += ' AND region = ?';
      params.push(region);
    }
    if (search) {
      query += ' AND (name LIKE ? OR description LIKE ?)';
      countQuery += ' AND (name LIKE ? OR description LIKE ?)';
      params.push(`%${search}%`, `%${search}%`);
    }

    query += ` ORDER BY ${sort === 'name' ? 'name ASC' : 'id ASC'} LIMIT ? OFFSET ?`;
    
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
    const [states] = await pool.query('SELECT * FROM states WHERE id = ?', [req.params.id]);
    if (states.length === 0) {
      return res.status(404).json({ success: false, message: 'State not found' });
    }

    const stateId = req.params.id;
    const [heritageCount] = await pool.query('SELECT COUNT(*) as count FROM heritage_sites WHERE state_id = ?', [stateId]);
    const [festivalCount] = await pool.query('SELECT COUNT(*) as count FROM festivals WHERE state_id = ?', [stateId]);
    const [foodCount] = await pool.query('SELECT COUNT(*) as count FROM foods WHERE state_id = ?', [stateId]);

    const state = states[0];
    state.counts = {
      heritage: heritageCount[0].count,
      festivals: festivalCount[0].count,
      foods: foodCount[0].count
    };

    res.json({ success: true, data: state });
  } catch (error) {
    next(error);
  }
});

module.exports = router;

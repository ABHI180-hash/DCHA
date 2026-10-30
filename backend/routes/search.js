const express = require('express');
const router = express.Router();
const pool = require('../config/database');

router.get('/', async (req, res, next) => {
  try {
    const { q, category, state_id } = req.query;
    if (!q) {
      return res.status(400).json({ success: false, message: 'Search query is required' });
    }

    const searchQuery = `%${q}%`;
    const results = {};
    const params = state_id ? [state_id, searchQuery, searchQuery] : [searchQuery, searchQuery];
    const stateFilter = state_id ? ' AND state_id = ?' : '';

    const tables = [
      { name: 'heritage_sites', titleCol: 'name', descCol: 'description' },
      { name: 'festivals', titleCol: 'name', descCol: 'description' },
      { name: 'foods', titleCol: 'name', descCol: 'description' },
      { name: 'traditions', titleCol: 'name', descCol: 'description' },
      { name: 'dances', titleCol: 'name', descCol: 'description' },
      { name: 'music', titleCol: 'name', descCol: 'description' },
      { name: 'arts_crafts', titleCol: 'name', descCol: 'description' },
      { name: 'folk_stories', titleCol: 'title', descCol: 'story_text' },
      { name: 'traditional_dresses', titleCol: 'name', descCol: 'description' },
      { name: 'languages', titleCol: 'name', descCol: 'description' }
    ];
    
    let total = 0;

    for (const t of tables) {
      if (category && category !== t.name) continue;
      
      const query = `SELECT id, ${t.titleCol} as name, ${t.descCol} as description, '${t.name}' as type FROM ${t.name} WHERE 1=1 ${stateFilter} AND (${t.titleCol} LIKE ? OR ${t.descCol} LIKE ?) LIMIT 10`;
      const [rows] = await pool.query(query, params);
      
      const countQuery = `SELECT COUNT(*) as count FROM ${t.name} WHERE 1=1 ${stateFilter} AND (${t.titleCol} LIKE ? OR ${t.descCol} LIKE ?)`;
      const [countRows] = await pool.query(countQuery, params);
      
      results[t.name] = {
        items: rows,
        count: countRows[0].count
      };
      total += countRows[0].count;
    }

    res.json({ success: true, data: results, total });
  } catch (error) {
    next(error);
  }
});

module.exports = router;

const express = require('express');
const router = express.Router();
const pool = require('../config/database');

router.get('/', async (req, res, next) => {
  try {
    const { state1_id, state2_id } = req.query;
    if (!state1_id || !state2_id) {
      return res.status(400).json({ success: false, message: 'state1_id and state2_id required' });
    }

    const getFullStateData = async (stateId) => {
      const [states] = await pool.query('SELECT * FROM states WHERE id = ?', [stateId]);
      if (states.length === 0) return null;
      
      const state = states[0];
      
      // Fetch counts for comparison
      const tables = ['heritage_sites', 'festivals', 'foods', 'traditions', 'dances', 'music', 'arts_crafts'];
      state.counts = {};
      
      for (const table of tables) {
        const [rows] = await pool.query(`SELECT COUNT(*) as count FROM ${table} WHERE state_id = ?`, [stateId]);
        state.counts[table] = rows[0].count;
      }
      
      return state;
    };

    const state1 = await getFullStateData(state1_id);
    const state2 = await getFullStateData(state2_id);

    if (!state1 || !state2) {
      return res.status(404).json({ success: false, message: 'One or both states not found' });
    }

    res.json({ success: true, data: { state1, state2 } });
  } catch (error) { next(error); }
});

module.exports = router;

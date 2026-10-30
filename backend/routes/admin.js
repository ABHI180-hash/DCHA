const express = require('express');
const router = express.Router();
const pool = require('../config/database');
const { authenticate, requireAdmin } = require('../middleware/auth');

router.use(authenticate, requireAdmin);

router.get('/dashboard', async (req, res, next) => {
  try {
    const tables = ['users', 'states', 'heritage_sites', 'festivals', 'contributions'];
    const counts = {};
    for (const table of tables) {
      const [rows] = await pool.query(`SELECT COUNT(*) as count FROM ${table}`);
      counts[table] = rows[0].count;
    }
    res.json({ success: true, data: counts });
  } catch (error) { next(error); }
});

router.get('/contributions', async (req, res, next) => {
  try {
    const { status } = req.query;
    let query = 'SELECT c.*, u.name as user_name FROM contributions c LEFT JOIN users u ON c.user_id = u.id';
    const params = [];
    if (status) {
      query += ' WHERE c.status = ?';
      params.push(status.toUpperCase());
    }
    query += ' ORDER BY c.created_at DESC';
    const [rows] = await pool.query(query, params);
    res.json({ success: true, data: rows });
  } catch (error) { next(error); }
});

router.patch('/contributions/:id', async (req, res, next) => {
  try {
    const { status } = req.body;
    const upperStatus = status ? status.toUpperCase() : '';
    if (!['APPROVED', 'REJECTED', 'PENDING'].includes(upperStatus)) {
      return res.status(400).json({ success: false, message: 'Invalid status' });
    }
    await pool.query('UPDATE contributions SET status = ? WHERE id = ?', [upperStatus, req.params.id]);
    res.json({ success: true, message: 'Contribution updated' });
  } catch (error) { next(error); }
});

router.get('/users', async (req, res, next) => {
  try {
    const [rows] = await pool.query('SELECT id, name, email, role, created_at FROM users ORDER BY created_at DESC');
    res.json({ success: true, data: rows });
  } catch (error) { next(error); }
});

router.get('/states', async (req, res, next) => {
  try {
    const [rows] = await pool.query('SELECT * FROM states ORDER BY name ASC');
    res.json({ success: true, data: rows });
  } catch (error) { next(error); }
});

router.post('/states', async (req, res, next) => {
  try {
    const { name, region, description } = req.body;
    const [result] = await pool.query('INSERT INTO states (name, region, description) VALUES (?, ?, ?)', [name, region, description]);
    res.status(201).json({ success: true, id: result.insertId });
  } catch (error) { next(error); }
});

router.put('/states/:id', async (req, res, next) => {
  try {
    const { name, region, description } = req.body;
    await pool.query('UPDATE states SET name = ?, region = ?, description = ? WHERE id = ?', [name, region, description, req.params.id]);
    res.json({ success: true, message: 'State updated' });
  } catch (error) { next(error); }
});

router.get('/heritage', async (req, res, next) => {
  try {
    const [rows] = await pool.query('SELECT h.*, s.name as state_name FROM heritage_sites h LEFT JOIN states s ON h.state_id = s.id ORDER BY h.created_at DESC');
    res.json({ success: true, data: rows });
  } catch (error) { next(error); }
});

router.post('/heritage', async (req, res, next) => {
  try {
    const { state_id, name, description, category } = req.body;
    const [result] = await pool.query('INSERT INTO heritage_sites (state_id, name, description, category) VALUES (?, ?, ?, ?)', [state_id, name, description, category]);
    res.status(201).json({ success: true, id: result.insertId });
  } catch (error) { next(error); }
});

router.put('/heritage/:id', async (req, res, next) => {
  try {
    const { state_id, name, description, category } = req.body;
    await pool.query('UPDATE heritage_sites SET state_id = ?, name = ?, description = ?, category = ? WHERE id = ?', [state_id, name, description, category, req.params.id]);
    res.json({ success: true, message: 'Heritage updated' });
  } catch (error) { next(error); }
});

router.delete('/heritage/:id', async (req, res, next) => {
  try {
    await pool.query('DELETE FROM heritage_sites WHERE id = ?', [req.params.id]);
    res.json({ success: true, message: 'Heritage deleted' });
  } catch (error) { next(error); }
});

module.exports = router;

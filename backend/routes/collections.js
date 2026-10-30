const express = require('express');
const router = express.Router();
const pool = require('../config/database');
const { authenticate } = require('../middleware/auth');

router.get('/', authenticate, async (req, res, next) => {
  try {
    const [collections] = await pool.query('SELECT * FROM collections WHERE user_id = ? ORDER BY created_at DESC', [req.user.id]);
    
    // Get items for each collection
    for (let c of collections) {
      const [items] = await pool.query('SELECT * FROM collection_items WHERE collection_id = ?', [c.id]);
      c.items = items;
    }
    
    res.json({ success: true, data: collections });
  } catch (error) { next(error); }
});

router.post('/', authenticate, async (req, res, next) => {
  try {
    const { name, description } = req.body;
    if (!name) return res.status(400).json({ success: false, message: 'Name required' });

    const [result] = await pool.query('INSERT INTO collections (user_id, name, description) VALUES (?, ?, ?)', [req.user.id, name, description]);
    res.status(201).json({ success: true, data: { id: result.insertId, name, description } });
  } catch (error) { next(error); }
});

router.post('/:id/items', authenticate, async (req, res, next) => {
  try {
    const collectionId = req.params.id;
    const { item_type, item_id } = req.body;

    const [collections] = await pool.query('SELECT id FROM collections WHERE id = ? AND user_id = ?', [collectionId, req.user.id]);
    if (collections.length === 0) return res.status(404).json({ success: false, message: 'Collection not found' });

    const [result] = await pool.query('INSERT INTO collection_items (collection_id, item_type, item_id) VALUES (?, ?, ?)', [collectionId, item_type, item_id]);
    res.status(201).json({ success: true, message: 'Item added to collection' });
  } catch (error) { next(error); }
});

router.delete('/:collectionId/items/:itemId', authenticate, async (req, res, next) => {
  try {
    const { collectionId, itemId } = req.params;
    
    const [collections] = await pool.query('SELECT id FROM collections WHERE id = ? AND user_id = ?', [collectionId, req.user.id]);
    if (collections.length === 0) return res.status(404).json({ success: false, message: 'Collection not found' });

    await pool.query('DELETE FROM collection_items WHERE collection_id = ? AND item_id = ?', [collectionId, itemId]);
    res.json({ success: true, message: 'Item removed from collection' });
  } catch (error) { next(error); }
});

module.exports = router;

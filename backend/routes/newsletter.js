const express = require('express');
const router = express.Router();
const pool = require('../config/database');

router.post('/subscribe', async (req, res, next) => {
  try {
    const { email } = req.body;
    if (!email) return res.status(400).json({ success: false, message: 'Email required' });

    // Assuming we have a newsletter_subscribers table
    await pool.query('INSERT IGNORE INTO newsletter_subscribers (email) VALUES (?)', [email]);
    res.status(201).json({ success: true, message: 'Subscribed successfully' });
  } catch (error) { next(error); }
});

router.post('/contact', async (req, res, next) => {
  try {
    const { name, email, message } = req.body;
    if (!name || !email || !message) {
      return res.status(400).json({ success: false, message: 'Name, email, and message required' });
    }

    // Assuming we have a contact_messages table
    await pool.query('INSERT INTO contact_messages (name, email, message) VALUES (?, ?, ?)', [name, email, message]);
    res.status(201).json({ success: true, message: 'Message sent successfully' });
  } catch (error) { next(error); }
});

module.exports = router;

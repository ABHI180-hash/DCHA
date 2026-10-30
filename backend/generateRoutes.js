const fs = require('fs');
const path = require('path');

const generateListRoute = (tableName, filters = []) => {
  let filterString = '';
  filters.forEach(f => {
    if (f === 'state_id') {
      filterString += `    if (state_id) {\n      query += ' AND state_id = ?';\n      countQuery += ' AND state_id = ?';\n      params.push(state_id);\n    }\n`;
    } else if (f === 'category') {
      filterString += `    if (category) {\n      query += ' AND category = ?';\n      countQuery += ' AND category = ?';\n      params.push(category);\n    }\n`;
    } else if (f === 'month') {
      filterString += `    if (month) {\n      query += ' AND month = ?';\n      countQuery += ' AND month = ?';\n      params.push(month);\n    }\n`;
    } else if (f === 'era') {
      filterString += `    if (era) {\n      query += ' AND era = ?';\n      countQuery += ' AND era = ?';\n      params.push(era);\n    }\n`;
    } else if (f === 'section') {
      filterString += `    if (section) {\n      query += ' AND section = ?';\n      countQuery += ' AND section = ?';\n      params.push(section);\n    }\n`;
    } else if (f === 'risk_level') {
      filterString += `    if (risk_level) {\n      query += ' AND risk_level = ?';\n      countQuery += ' AND risk_level = ?';\n      params.push(risk_level);\n    }\n`;
    }
  });

  const queryParams = ['search', 'page = 1', 'limit = 50', ...filters].join(', ');

  return `const express = require('express');
const router = express.Router();
const pool = require('../config/database');

router.get('/', async (req, res, next) => {
  try {
    const { ${queryParams} } = req.query;
    const offset = (page - 1) * limit;

    let query = 'SELECT * FROM ${tableName} WHERE 1=1';
    let countQuery = 'SELECT COUNT(*) as total FROM ${tableName} WHERE 1=1';
    const params = [];

${filterString}    if (search) {
      query += ' AND (name LIKE ? OR description LIKE ?)';
      countQuery += ' AND (name LIKE ? OR description LIKE ?)';
      params.push(\`%\${search}%\`, \`%\${search}%\`);
    }

    query += \` ORDER BY name ASC LIMIT ? OFFSET ?\`;
    
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
`;
};

const routes = {
  traditions: { table: 'traditions', filters: ['state_id'] },
  dances: { table: 'dances', filters: ['state_id'] },
  music: { table: 'music', filters: ['state_id'] },
  artsCrafts: { table: 'arts_crafts', filters: ['state_id'] },
  gallery: { table: 'gallery', filters: ['state_id'] },
  timeline: { table: 'timeline', filters: ['state_id', 'era', 'category'] },
  audio: { table: 'audio', filters: ['state_id'] },
  calendar: { table: 'calendar', filters: ['state_id', 'month'] },
  heritageRisk: { table: 'heritage_risk', filters: ['state_id', 'risk_level'] },
  dresses: { table: 'dresses', filters: ['state_id'] },
  languages: { table: 'languages', filters: ['state_id'] }
};

for (const [routeName, config] of Object.entries(routes)) {
  const content = generateListRoute(config.table, config.filters) + '\nmodule.exports = router;\n';
  fs.writeFileSync(path.join(__dirname, 'routes', \`\${routeName}.js\`), content);
}

// Special routes
const museumContent = generateListRoute('museum', ['state_id', 'section', 'category']) + `
router.get('/:id', async (req, res, next) => {
  try {
    const [rows] = await pool.query('SELECT * FROM museum WHERE id = ?', [req.params.id]);
    if (rows.length === 0) return res.status(404).json({ success: false, message: 'Artifact not found' });
    res.json({ success: true, data: rows[0] });
  } catch (error) { next(error); }
});
module.exports = router;
`;
fs.writeFileSync(path.join(__dirname, 'routes', 'museum.js'), museumContent);

const storiesContent = generateListRoute('stories', ['state_id']) + `
router.get('/:id', async (req, res, next) => {
  try {
    const [rows] = await pool.query('SELECT * FROM stories WHERE id = ?', [req.params.id]);
    if (rows.length === 0) return res.status(404).json({ success: false, message: 'Story not found' });
    res.json({ success: true, data: rows[0] });
  } catch (error) { next(error); }
});
module.exports = router;
`;
fs.writeFileSync(path.join(__dirname, 'routes', 'stories.js'), storiesContent);

console.log('Routes generated successfully.');

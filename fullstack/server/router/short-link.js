const express = require('express');
const link = require('./../controllers/link.controller');

const router = express.Router();

router.get('/', (req, res) => {
  res.send('welcome to short-link service')
});

router.get('/getOriginLink', link.find);

router.post('/generateShortLink', link.create);

module.exports = router;

var express = require('express');
var router = express.Router();
var domainData = require('../utils/db/handle');

router.post('/saveDomain', function (req, res, next) {
    domainData.add(req, res, next)
});

router.get('/queryDomain/:domainName', function(req, res, next) {
    domainData.query(req, res, next)
});

module.exports = router;

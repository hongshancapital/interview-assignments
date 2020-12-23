const express = require('express');
const router = express.Router();
// load URL model
const URL = require('../../models/URLs');

// @route GET api/redirect
// e.g. localhost:5000/api/redirect?hash=cmkslh8kj0vtbrp
router.get('/', (req, res) => {
    
    const hash = req.headers.hash;

    URL.findOne({_id: hash })
        .then((doc) => {
            return res.json({ url: doc.url })
        })
        .catch((err) => {
            return res.status(400).json({ error: 'This link may have expried'});
        })

});

module.exports = router;
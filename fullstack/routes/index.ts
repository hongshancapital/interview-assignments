import express from 'express';

import Connection from '../config/db';
import convertDomain from '../library/factory';

Connection.connect((err) => {
    if (err) console.error(`OOPS! Please check the database connection with below error. \n ${err}`)
});

var router = express.Router();

router.post('/', function(req, res) {
    const { origUrl } = req.body;
    convertDomain(origUrl).convert(function(err, convertedUrl) {
        if (err) {
            console.log('Operated Database Error:')
            console.error(err);
            return;
        }
        res.send(convertedUrl)
    })
});

export default router;
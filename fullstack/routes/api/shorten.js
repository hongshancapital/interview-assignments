const express = require('express');
const router = express.Router();
const uniqid = require('uniqid');
// load URL model
const URL = require('../../models/URLs');

// @route GET /api/shorten/test
// router.get('/test', (req, res) => res.json({msg: 'API is working'}));

// @route POST api/shorten
// e.g. localhost:5000/api/shorten?url=www.google.com
router.post('/', (req, res) => {
    if(req.body.url) {
        urlData = req.body.url;
    }
    // check if URL exist
    URL.findOne({url: urlData}, (err, doc) => {
        if(doc){
            console.log('Entry found it in db.');
        }else{
            const webAdress = new URL({
                _id: uniqid(),
                url: urlData
            });
            webAdress.save((err) => {
                if(err){
                    return console.error(err);
                }
                res.send({
                    url: urlData,
                    hash: webAdress._id,
                    status: 200,
                    statusTxt: 'OK'
                })
            })
        }
    });

})

module.exports = router;
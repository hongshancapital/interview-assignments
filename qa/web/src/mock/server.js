var express = require('express');
var bodyParser = require('body-parser')
var app = express();
app.use(bodyParser.json())

app.all('*', function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Content-Type,XFILENAME,XFILECATEGORY,XFILESIZE");
    res.header("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
    res.header("X-Powered-By",' 3.2.1')
    res.header("Content-Type", "application/json;charset=utf-8");
    if (req.method == "OPTIONS") {
        res.sendStatus(200);
    } else {
        next();
    }
});

var fs = require('fs');

// api : /toolbox
app.get('/toolbox', function(req, res) {
    console.log('/toolbox')
    try {
        var data = fs.readFileSync('./src/mock/data/toolbox.json')
        res.json(JSON.parse(data));
    } catch(err) {
        console.error(err);
        res.json({});
    }
});

app.get('/toolbox/options', function(req, res) {
    console.log('/toolbox/options')
    try {
        var data = fs.readFileSync('./src/mock/data/options.json');
        res.json(JSON.parse(data));
    } catch(err) {
        console.error(err);
        res.json([]);
    }

});

// write data
app.post('/toolbox/create', function(req, res) {
    console.log('/toolbox/create')
    reqJson = JSON.parse(JSON.stringify(req.body))
    if (!reqJson.hasOwnProperty("name")) {
        res.json({"error":"No name specified"});

    } else {
        data = {"message":"success","data":[]}
        reqJson.id = 1
        data.data[0] = reqJson
        try {
            fs.writeFileSync('./src/mock/data/toolbox.json', JSON.stringify(data))
            res.json({"message":"success"})
        } catch(error) {
            console.error(err);
            res.json({});
        }
    }
});

app.listen('5000', function () {
    console.log('Example app listening on port 5000!');
});

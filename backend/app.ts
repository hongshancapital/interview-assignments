import express = require('express');
const app: express.Application = express();
var apiRouter = require('../routes/api');

app.get('/',function(req: any, res: { send: (arg0: string) => void; }){
    res.send('Hello HS!');
});

app.use('/api', apiRouter);

app.listen(1234,function(){
    console.log('Example app listening on port 1234!');
})

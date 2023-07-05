import express from 'express'
import domainRouter from '../router/domain'
import bodyParser from 'body-parser'

const app: express.Application = express()
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));
app.get('/', (req, res)=>{
    res.send('Default')
})
app.use('/domain', domainRouter)

app.listen(3000, ()=>{
    console.log('app init')
})

process.on('uncaughtException', function (err) {
    console.log("node崩溃", err.toString())
});
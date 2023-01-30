import express from 'express'
import {Bucket} from './type/Bucket'
import {MemorySlotRepos} from './repos/MemorySlotRepos'
import {MysqlSlotRepos} from './repos/MysqlSlotRepos'
import {AppDataSource} from "./config/DbConnection"
import { DataNode } from './type/DataNode'

const app = express();
const PORT = 9999;

app.use(express.urlencoded());
app.use(express.json());

const mysqlRepos:MysqlSlotRepos = new MysqlSlotRepos();
const slotRepos:MemorySlotRepos = new MemorySlotRepos(10000, mysqlRepos);
const bucket:Bucket = new Bucket(1000, slotRepos);

app.post('/code', (req, res) => {
    let { code } = req.body;
    if (code == null || !code?.trim()) {
        let respData = {'code':503, 'msg':'bad request data'};
        res.send(respData);
        return;
    }
    bucket.getByCode(code, (err:string, data:DataNode) => {
        if (err != null) {
            let respData = {'code':500, 'data':err};
            res.send(respData);
        } else {
            if (data == null) {
                let respData = {'code':200, 'data':""};
                res.send(respData);
            } else {
                let respData = {'code':200, 'data':data.origin};
                res.send(respData);
            }
        }
    });
});
6
app.post('/origin', (req, res) => {
    let { origin } = req.body;
    if (origin == null || !origin?.trim()) {
        let respData = {'code':503, 'msg':'bad request data'};
        res.send(respData);
        return; 
    }
    bucket.getByOrigin(origin, (err:string, data:DataNode) => {
        if (err != null) {
            let respData = {'code':500, 'data':err};
            res.send(respData);
        } else {
            if (data == null) {
                let respData = {'code':200, 'data':""};
                res.send(respData);
            } else {
                let respData = {'code':200, 'data':data.code};
                res.send(respData);
            }
        }
    });
});

AppDataSource.initialize().then(() => {
    app.listen(PORT, ()=> {
        console.log('app listening on port ' + PORT);
    });
})
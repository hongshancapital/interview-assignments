
import './db/mongo';
import {createServer} from './util/server';

createServer().then(server => {
    server.listen(3001, () => {
        console.log('启动成功 http://127.0.0.1:3001');
    })
}).catch(err => {
    console.log("启动失败");
})

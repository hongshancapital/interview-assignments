import Koa from 'koa'
import { bodyParser } from '@koa/bodyparser'
import router from '@/router';
import { APP_PORT } from '@/constant';
import { setupDB } from '@/data/datasource';

const app = new Koa();
app.use(bodyParser())
app.use(router.routes());
app.use(router.allowedMethods());

const appPromise = new Promise<typeof app>(res => {
    setupDB().then(() => {
        app.listen(APP_PORT, () => { console.log(`server started on ${APP_PORT}`) })
        res(app)
    }).catch(err => {
        console.error(err)
        throw new Error('start up error')
    });
})

export { appPromise }

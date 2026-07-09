import Koa from 'koa';
import bodyparser from 'koa-bodyparser';
import routes from './routes';

const app = new Koa();

app.use(bodyparser());
app.use(routes.routes());

export default app;

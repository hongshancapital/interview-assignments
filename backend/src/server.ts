import App from '@/app';
import IndexRoute from '@routes/index.route';
import UrlRoute from '@/routes/urls.route';
import validateEnv from '@utils/validateEnv';

validateEnv();

const app = new App([new IndexRoute(), new UrlRoute()]);

app.listen();

import App from './app';
import IndexRoute from '@routes/index.route';
import ShortUrlRoute from '@routes/short-url.route';

const app = new App([new IndexRoute(), new ShortUrlRoute()]);

app.listen();

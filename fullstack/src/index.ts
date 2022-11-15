import app from './app';
import config from './config/index';

const port = process.env.PORT || config.PORT;

app.listen(port, () => {
  console.log('Server is running at http://127.0.0.1:' + port);
});

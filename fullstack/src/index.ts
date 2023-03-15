/** @format */
import { App } from './app';

const port = 3000;

const app = new App();

app.init().then(res => {
  app.express.listen(port, () => {
    console.log(`Server started on port ${port}`);
  });
});

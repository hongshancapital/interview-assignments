import { App } from './app'

new App().start().listen(process.env.SERVER_PORT, () => {
  console.log(`⚡️[server]: Server is running at http://localhost:${process.env.SERVER_PORT}`);
});

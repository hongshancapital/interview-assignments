import express, { Application } from 'express';
import registerUriController from './uri/controller';

const app: Application = express();

app.use(express.json())

registerUriController(app);

app.get("/", (req, res) => {
  res.send("Welcome to Stephen Short Domain Uri Service!");
});

export default app;
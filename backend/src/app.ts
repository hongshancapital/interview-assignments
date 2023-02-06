import express from 'express';
import sequelize from './models/config';
import routers from './routers';

const app = express();
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.use(routers);

sequelize.sync();

export default app;
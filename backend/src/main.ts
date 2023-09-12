import express from 'express';
import bodyParser from 'body-parser';
import config from './config/config';
import {router} from "./routers/routers";
const app = express();
const port = config.serverPort || 8080;
let mongoose =  require('mongoose');
mongoose.connect(config.mongodb)
app.use(bodyParser.json());

app.use("/",router);

app.listen(port, () => {
    console.log(`Server is listening on port ${port}`);
});

export default  app;
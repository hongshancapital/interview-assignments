import express from "express";
import routers from "./routes/api";

const app = express();
const port = process.env.PORT || '8000';

app.use(express.urlencoded({ extended: true }));
app.use(routers);

app.listen(port);


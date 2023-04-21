import express from "express";
import mongoose from "mongoose";
import routes from "./routes";

const app = express();

mongoose.connect("mongodb://10.0.68.132:27017/test");

app.use(express.json());
app.use(routes);

export default app;

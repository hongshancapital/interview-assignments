import express, { Application, Request, Response } from "express";
import generatorRouter from "./src/generator";
import tinyurlRouter from "./src/tinyurl";

const app: Application = express();
const port = 8080;

app.use(generatorRouter);
app.use(tinyurlRouter);

try {
    app.listen(port, (): void => {
        console.log(`Connected successfully on port ${port}`);
    });
} catch (e) {
    console.error(`Error occured: ${(e as Error).message}`);
}

export default app;

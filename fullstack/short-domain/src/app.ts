import express, { Express , Request, Response } from "express";
import infoRouter from './router/info';

const app: Express = express();

/**
 * 加载info router
 */
app.use('/info', infoRouter);

/**
 * 短域名服务
 */
app.get("/:shortId", (req: Request, res: Response) => {
    console.log(req.params.shortId);
    console.log(req.body);
    res.send("Hello World");
});

app.put("/add", (req: Request, res: Response) => {
    console.log(req.body);
})

app.listen(3000, () => {
    console.log("Server running on port 3000");
});

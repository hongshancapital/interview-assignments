import { Application, Request, Response } from 'express';
import { connectToRedis, getDbSize, convert10To62, getShortUri, saveUri, getUri, isValidUri } from "./service";

const registerController = (app: Application) => {

    app.post("/uri", async (req: Request<{}, {}, { uri: string;}>, res: Response) => {
        await connectToRedis();
        const host = req.headers.host;
        const base = await getDbSize();
        const shortKey = convert10To62(base);
        const longUri = req.body.uri;
        if (!longUri || !isValidUri(longUri)) {
            return res.sendStatus(400);
        }
        saveUri(shortKey!, longUri);
        res.send(getShortUri(host!, shortKey!));
    });

    app.get("/uri/:key", async (req: Request<{ key: string}>, res: Response) => {
        await connectToRedis();
        const key = req.params.key;
        const longUri = await getUri(key);
        if (!longUri) {
            return res.sendStatus(400);
        }
        res.redirect(301, longUri);
    });
}

export default registerController;
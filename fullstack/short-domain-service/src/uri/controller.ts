import { connectToRedis, getDbSize, convert10To62, getShortUri, saveUri, getUri, isValidUri } from "./service";

const registerController = (app: any) => {
    app.post("/uri", async (req: any, res: any) => {
        await connectToRedis();
        const base = await getDbSize();
        const shortKey = convert10To62(base);
        const longUri = req.body["uri"];
        if (!longUri || !isValidUri(longUri)) {
            return res.sendStatus(400);
        }
        saveUri(shortKey!, longUri);
        res.send(getShortUri(req.headers.host, shortKey!));
    });

    app.get("/uri/:key", async (req: any, res: any) => {
        await connectToRedis();
        const key = req.params['key'];
        const longUri = await getUri(key);
        if (!longUri) {
            return res.sendStatus(400);
        }
        res.redirect(301, longUri);
    });
}

export default registerController;
const { connectToRedis, getDbSize, convert10To62, getShortUri, saveUri, getUri, isValidUri } = require("./service");

const registerController = (app) => {
    app.post("/uri", async (req, res) => {
        await connectToRedis();
        const base = await getDbSize();
        const shortKey = convert10To62(base);
        const longUri = req.body["uri"];
        if (!longUri || !isValidUri(longUri)) {
            return res.sendStatus(400);
        } 
        saveUri(shortKey, longUri);
        res.send(getShortUri(req.headers.host, shortKey));
    });

    app.get("/uri/:key", async (req, res) => {
        await connectToRedis();
        const key = req.params['key'];
        const longUri = await getUri(key);
        if (!longUri) {
            return res.sendStatus(400);
        }
        res.redirect(301, longUri);
    });
}

module.exports = registerController;
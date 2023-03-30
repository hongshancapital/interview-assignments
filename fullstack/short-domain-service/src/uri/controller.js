const { connectToRedis, getDbSize, convert10To62, getShortUri } = require("./service");

const registerController = (app) => {
    app.post("/uri", async (req, res) => {
        await connectToRedis();
        const base = await getDbSize();
        const shortKey = convert10To62(base);
        res.send(getShortUri(req.headers.host, shortKey));
    });
}

module.exports = registerController;
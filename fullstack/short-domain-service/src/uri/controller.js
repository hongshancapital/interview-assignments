const { connectToRedis, getDbSize, convert10To62} = require("./service");

const registerController = (app) => {
    app.post("/uri", async (req, res) => {
        const getShortUri = key => {
            return `${req.headers.host}/uri/${key}`;
        }
        await connectToRedis();
        const base = await getDbSize();
        const shortKey = convert10To62(base);
        res.send(getShortUri(shortKey));
    });
}

module.exports = registerController;
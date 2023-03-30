const { connectToRedis, getDbSize, convert10To62} = require("./service");

const registerController = (app) => {
    app.post("/uri", async (req, res) => {
        await connectToRedis();
        const base = await getDbSize();
        const shortKey = convert10To62(base);
        res.send(String(shortKey));
    });
}

module.exports = registerController;
const registerController = (app) => {
    app.post("/uri", (req, res) => {
        res.send('1fcde');
    });
}

module.exports = registerController;
const express = require("express");
const registerUriController = require('./uri/controller');
const app = express();

registerUriController(app);

app.get("/", (req, res) => {
  res.send("Welcome to Stephen Short Domain Uri Service!");
});

module.exports = app;
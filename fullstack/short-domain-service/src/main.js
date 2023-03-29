const express = require("express");
const app = express();

app.get("/", (req, res) => {
  res.send("Welcome to Stephen Short Domain Uri Service!");
});

module.exports = app;
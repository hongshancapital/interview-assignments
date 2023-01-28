import Express from "express";
import { nanoid } from "nanoid";

const cors = require("cors");
const bodyParser = require("body-parser");
const mongoose = require("mongoose");
const ShortUrl = require("./models/shortUrl");
const app = Express();

mongoose.connect("mongodb://127.0.0.1/urlShortener", {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});

app.use(cors());
app.use(Express.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.post("/w/shrink", async (req: Express.Request, res: Express.Response) => {
  const data = {
    fullUrl: req.body.fullUrl,
    shortUrl: "",
  };

  const RETRY_MAX = 3;
  let tryTimes = 0;
  console.log(44);
  while (tryTimes < RETRY_MAX) {
    const url = nanoid(8);
    console.log('url', url);
    const temp = await ShortUrl.findOne({ shortUrl: url });

    if (temp === null) {
      data.shortUrl = url;
      break;
    }
    tryTimes++;
  }

  if (!data.shortUrl) {
    res.status(500).send({ error: "short url generate failed" });
    return;
  }

  try {
    await ShortUrl.create(data);
    res.send(data);
  } catch (e) {
    res.status(500).send({ error: "system error" });
  }
});

app.get("/r/search", async (req, res) => {
  if (req.query.shortUrl) {
    try {
      const urlData = await ShortUrl.findOne({ shortUrl: req.query.shortUrl });
      if (urlData == null) return res.send([]);
      res.send([urlData]);
    } catch (e) {
      res.status(500).send({ error: "system error" });
    }
  } else {
    try {
      const urlList = await ShortUrl.find();
      res.send(urlList);
    } catch (e) {
      res.status(500).send({ error: "system error" });
    }
  }
});

app.listen(process.env.PORT || 5000);

module.exports = app;

// mongod --config /usr/local/etc/mongod.conf

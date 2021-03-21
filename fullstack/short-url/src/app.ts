import express from "express";
import { getOriginUrl, genShortUrl } from "./controllers/api";
import { redirectToOriginUrl,reset } from "./controllers/shorturl";

import { notFound } from "./const";
import path from "path";

// Create Express server
const app = express();

app.set("port", process.env.PORT || 3000);

app.use(
  express.static(path.join(__dirname, "../public"), { maxAge: 31557600000 })
);

app.get("/originurl", getOriginUrl);
app.get("/shorturl", genShortUrl);

app.get("/reset",reset);
app.get("*",redirectToOriginUrl);

app.use((req, res) => {
  res.status(404).end(notFound);
});

export default app;

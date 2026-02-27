import express from "express";
import { findLongUrlById, findIdByLongUrl, save } from "./db";

export const app = express();
const PORT = 3000;
const charSet: string =
  "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

function idToShortUrl(id: number): string {
  let shortUrl: string = "";
  while (id > 0) {
    const remainder: number = id % 62;
    shortUrl = charSet[remainder] + shortUrl;
    id = Math.floor(id / 62);
  }
  return shortUrl;
}

function shortUrlToId(shortUrl: string): number {
  let id: number = 0;
  for (let i = 0; i < shortUrl.length; i++) {
    const charIndex: number = charSet.indexOf(shortUrl[i]);
    id = id * 62 + charIndex;
  }
  return id;
}

app.get("/save/:longUrl", async (req, res) => {
  const longUrl: string = req.params.longUrl;

  const id: number = await findIdByLongUrl(longUrl);
  if (id) {
    res.status(208).send(idToShortUrl(id));
    return;
  }

  const insertId = await save(longUrl);

  res.status(201).send(idToShortUrl(insertId));
});

app.get("/load/:shortUrl", async (req, res) => {
  const shortUrl = req.params.shortUrl;

  const id = shortUrlToId(shortUrl);
  const longUrl = await findLongUrlById(id);
  if (longUrl) {
    res.send(`${longUrl}`);
    return;
  }

  res.status(404).send(`${shortUrl}: not found`);
});

app.listen(PORT, () => {
  console.log(`Express with Typescript! http://localhost:${PORT}`);
});

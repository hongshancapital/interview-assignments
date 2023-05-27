import express, { Request, Response } from "express";
import { parseShortUrl, shortenUrl } from "./service";
import "./common";

const app = express();
app.use(express.json());

app.post("/api/short-url", async (req: Request, res: Response) => {
  const { url } = req.body;
  if (!url) {
    return res.status(400).json({ error: "url is required" });
  }
  try {
    const urlObj = new URL(url);
    if (urlObj.protocol !== "http:" && urlObj.protocol !== "https:") {
      return res.status(400).json({ error: "invalid http url" });
    }
  } catch (e) {
    return res.status(400).json({ error: "invalid url" });
  }
  return res.json({
    shortUrlKey: await shortenUrl(url),
  });
});

app.post("/api/short-url/parse", async (req: Request, res: Response) => {
  const { shortUrlKey } = req.body;
  if (!shortUrlKey) {
    return res.status(400).json({ error: "shortUrlKey is required" });
  }
  const parsedUrl = await parseShortUrl(shortUrlKey);
  console.info("parsed url: ", parsedUrl);
  if (!parsedUrl) {
    return res.status(404).json({ error: "not found" });
  }
  return res.json({
    url: parsedUrl,
  });
});

app.listen(3000, () => {
  console.log("Server started on port 3000");
});

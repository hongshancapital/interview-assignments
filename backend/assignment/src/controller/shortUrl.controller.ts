import { Router } from "express";
import { ShortUrlService } from "../service/shortUrl.service";
const router = Router();
const shortUrlService: ShortUrlService = new ShortUrlService();

router.post("/saveLongUrl", async (req, res) => {
  const { longUrl } = req.body;
  const shortUrl = await shortUrlService.saveLongUrl(longUrl);
  if (shortUrl) {
    res.json({ success: true, result: { shortUrl } });
    return;
  }
  res.json({ success: false, msg: "Invalid Url!" });
});

router.get("/fetchLongUrl", async (req, res) => {
  let { shortUrl } = req.query;
  shortUrl = shortUrl ? shortUrl.toString() : "";
  const longUrl = await shortUrlService.fetchLongUrl(shortUrl);
  if (longUrl) {
    res.json({ success: true, result: { longUrl } });
    return;
  }
  res.json({ success: false, msg: "Your short url not exists！" });
});

export default router;

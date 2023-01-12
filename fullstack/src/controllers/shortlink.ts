import { shortId } from "../utils/shortid";
import ShortLink from "../models/ShortLink";

/**
 * GET /
 * ShortLink.
 */
export async function getShortLink(req, res) {
  try {
    const shortLink = await ShortLink.findOne({ urlId: req.params.urlId });
    if (shortLink) {
      await ShortLink.updateOne(
        {
          urlId: req.params.urlId,
        }
      );
      return res.redirect(shortLink.origUrl);
    } else res.status(404).json({ msg: "Not found" });
  } catch (err) {
    console.log(err);
    res.status(500).json("Server Error");
  }
}

/**
 * POST /
 * 保存.
 */
export async function postShortLink(req, res) {
  const { origUrl } = req.body;
  const base = process.env.BASE || "";
  
  if (origUrl) {
    try {
      let url = await ShortLink.findOne({ origUrl });
      if (url) {
        res.json(url);
      } else {
        const urlId = shortId();
        const shortUrl = `${base}/${urlId}`;

        url = new ShortLink({
          origUrl,
          shortUrl,
          urlId
        });

        await url.save();
        res.json(url);
      }
    } catch (err) {
      console.log(err);
      res.status(500).json("Server Error");
    }
  } else {
    res.status(400).json({ msg: "Invalid URL" });
  }
}

import { Request, Response } from "express-serve-static-core";
import { nanoid } from "nanoid";
import express, { Router } from "express";

import UrlShorten from "../models/urls";
import { isValidUrl, getBaseUrl } from "../utils";
import { logger } from "../utils/logger";

const router: Router = express.Router();

/**
 * url shorten api
 * shortening rawUrl and return shortenUrl & urlCode
 */
router.route("/shorten").post(async (req: Request, res: Response) => {
  const { rawUrl } = req.body;

  if (!isValidUrl(rawUrl)) {
    return res.status(200).json({ message: "Invalid rawUrl" });
  }
  // generate an url code
  const urlCode = nanoid(8);
  try {
    let url = await UrlShorten.findOne({ rawUrl });
    if (url) {
      res.status(200).json({ data: url });
    } else {
      const baseUrl = getBaseUrl(rawUrl);
      const shortUrl = baseUrl + "/" + urlCode;

      url = new UrlShorten({
        rawUrl,
        shortenUrl: shortUrl,
        urlCode,
        createdAt: new Date().toLocaleString(),
      });

      await url.save();
      res.status(200).json({ data: url });
    }
  } catch (err: any) {
    logger.error("API_Request", err);
    res.status(500).json({ message: "Error thrown:" + err.message });
  }
});

/**
 * url query api
 * query rawUrl use urlCode
 */
router.route("/:urlCode").get(async (req: Request, res: Response) => {
  try {
    const urlCode = req.params.urlCode;
    const url = await UrlShorten.findOne({ urlCode });

    if (url) {
      return res.status(200).json({
        data: url,
      });
    }
    return res
      .status(404)
      .json({ message: `Not found related rawUrl with rawCode: ${urlCode}` });
  } catch (err: any) {
    logger.error("API_Request ", err);
    res.status(500).json({ message: "Error thrown:" + err.message });
  }
});

export default router;

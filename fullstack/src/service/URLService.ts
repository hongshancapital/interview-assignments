import express, { Request, Response } from "express";
import * as utils from "../utils/utils";
import { URLStore } from "../store/URLStore";

class URLService {
  private urlStore: URLStore;

  constructor(store: URLStore) {
    this.urlStore = store;
    this.getLongURL = this.getLongURL.bind(this);
    this.getShortURL = this.getShortURL.bind(this);
  }

  getLongURL(req: Request, res: Response) {
    const shortUrl = req?.params?.url;
    if (shortUrl == null || shortUrl.length !== utils.SHORT_URL_LENGTH) {
      return res.status(400).json({ data: "Invalid short URL" });
    }

    console.log("get shortUrl = ", shortUrl);

    if (this.urlStore.checkExist(shortUrl)) {
      let tmpUrl = this.urlStore.getLongURL(shortUrl) as string;
      let longUrl = utils.removeALLPadding(tmpUrl);
      console.log("get longUrl = ", longUrl);
      return res.json({ data: longUrl });
    } else {
      return res.status(400).json({
        data: "Invalid short URL",
      });
    }
  }

  getShortURL(req: Request, res: Response) {
    const longUrl = req?.body?.url;
    if (longUrl != null) {
      console.log("get longUrl = ", longUrl);

      if (!utils.checkUrlOK(longUrl)) {
        return res.status(400).json({ data: "Invalid URL" });
      } else {
        let newUrl = longUrl;
        let again = false;
        let shortUrl = "";
        do {
          console.log("newUrl = " + newUrl);
          shortUrl = utils.getHex32ID(newUrl);
          if (!this.urlStore.checkExist(shortUrl)) {
            //console.log("urlStore.checkExist false");
            this.urlStore.addUrlPairs(shortUrl, newUrl);
            again = false;
          } else {// hash冲突，相同的Hex32ID，不同的longUrl
            let tmpUrl = this.urlStore.getLongURL(shortUrl) as string;
            if (tmpUrl.toLocaleLowerCase() != newUrl.toLocaleLowerCase()) {
              newUrl = utils.addFixedPadding(newUrl);
              again = true;
            }
          }
        } while (again);
        console.log("shortUrl#" + shortUrl + "#");
        return res.json({ data: shortUrl });
      }
    } else {
      return res.status(400).json({ data: "Invalid URL" });
    }
  }
}

export { URLService };
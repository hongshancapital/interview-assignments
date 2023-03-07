import crypto from "crypto";
import Express from "express";
import { createConnection, Equal } from "typeorm";
import encodeNum from "./EncodeNum";
import { ShortURL } from "./orm/entities/ShortURL";
import splitShortToken, { legalHost, legalProtocol } from "./ShortToken";

const app = Express();

let urlRepository!: any;

function initConnection() {
  return createConnection().then((connection) => {
    urlRepository = connection.getRepository(ShortURL);
    app.emit("initialized");
  });
}

initConnection().then(() => {
  app.get("/long2short", async (req, res) => {
    let shortUrl = "";
    let longUrl = req.query["url"];
    if (typeof longUrl === "string") {
      const hash = crypto.createHash("md5");
      hash.update(longUrl);
      let result = hash.digest("hex");
      let urlInfo = await urlRepository.findOneBy({
        sign: Equal(result),
      });
      let shortLink = urlInfo?.shortLink;
      if (!urlInfo) {
        await urlRepository.save({
          url: longUrl,
          sign: result,
          shortLink: "0",
        });
        urlInfo = await urlRepository.findOneBy({
          sign: Equal(result),
        });
        shortLink = encodeNum(urlInfo!.id);
        await urlRepository.update(
          { id: urlInfo?.id },
          {
            shortLink,
          }
        );
      }

      shortUrl = legalProtocol + "//" + legalHost + "/" + shortLink;
    }
    res.send({
      shortUrl,
    });
  });

  app.get("/short2long", async (req, res) => {
    let longUrl: String | undefined = "";
    let shortUrl = req.query["url"];
    if (typeof shortUrl === "string") {
      let tokenInfo = splitShortToken(shortUrl);
      if (tokenInfo.isLegal) {
        let urlInfo = await urlRepository.findOneBy({
          shortLink: Equal(tokenInfo.path),
        });
        longUrl = urlInfo?.url;
      }
    }
    res.send({
      longUrl,
    });
  });
});
export default app;

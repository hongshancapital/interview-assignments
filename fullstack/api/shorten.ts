import mysqlFuncs from "../database/mysql";
import { base10ToBase21 } from "../util/util";

const ourHostname = "https://gitly.io/";

interface UrlShortenerRes {
  success: boolean;
  url: string;
  msg: string;
}

function urlShortener(oURL: string): Promise<UrlShortenerRes> {
  let ret: UrlShortenerRes;
  // check if this long url has been shortened ever
  return mysqlFuncs
    .findShortenedURLByOrignal(oURL)
    .then((res) => {
      if (res.includes("Failed to get the shortened URL from db.")) {
        throw res;
      }
      if (res) {
        ret = {
          success: false,
          url: res,
          msg: "original url has been created before",
        };
        return -1;
      } else {
        // get the max id, +1, then generate the shortened base21 number
        return mysqlFuncs.findMaxIdInTinyURL();
      }
    })
    .then((res) => {
      if (typeof res === "string") {
        throw res;
      }
      if (res === -1) {
        return -1;
      }
      if (res + 1 > Math.pow(21, 8)) {
        throw "exceed the length limitation";
      }
      // generate the short code
      return ourHostname + base10ToBase21(Number(res) + 1);
    })
    .then((res) => {
      if (res === -1) {
        return ret;
      } else {
        // try to save the shortened-original URL mapping to database
        return mysqlFuncs.insertTinyURL(res, oURL).then((result) => {
          if (typeof result === "string") {
            throw result;
          }
          ret = {
            success: true,
            url: res.toString(),
            msg: "shortened url created",
          };
          return ret;
        });
      }
    })
    .catch((err) => {
      ret = {
        success: false,
        url: oURL,
        msg: err,
      };
      throw ret;
    });
}

export default urlShortener;

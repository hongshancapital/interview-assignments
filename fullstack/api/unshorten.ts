import mysqlFuncs from "../database/mysql";

interface UrlUnshortenerRes {
  success: boolean;
  url: string;
  msg: string;
}

function urlUnshortener(sURL: string): Promise<UrlUnshortenerRes> {
  let ret: UrlUnshortenerRes;

  return mysqlFuncs.findOrignalURLByShort(sURL).then((res) => {
    if (res.includes("Failed to get the orignal URL from db.")) {
      throw {
        success: false,
        url: sURL,
        msg: res,
      };
    } else if (res.length > 0) {
      ret = {
        success: true,
        url: res,
        msg: "orignal url found",
      };
    } else {
      ret = {
        success: false,
        url: sURL,
        msg: "orignal url not found",
      };
    }
    return ret;
  });
}

export default urlUnshortener;

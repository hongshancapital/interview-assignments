import { chars } from "./EncodeNum";

export const legalHost = "s.cn";
export const legalProtocol = "https:";

export default function splitShortToken(rawUrl: string) {
  let url = "";
  let urlInfo;
  if (typeof rawUrl === "string") {
    try {
      url = decodeURIComponent(rawUrl);
      urlInfo = new URL(url);
    } catch (e) {
      return {
        isLegal: false,
      };
    }

    if (urlInfo.protocol === legalProtocol && urlInfo.host === legalHost) {
      let path = urlInfo.pathname;
      path = path.slice(1);
      if (path.length <= 8) {
        for (let i = 0; i < path.length; i++) {
          if (!chars.includes(path[i])) {
            return { isLegal: false };
          }
        }
        return { isLegal: true, path };
      }
    }
  }
  return { isLegal: false };
}

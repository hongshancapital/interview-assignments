
import validUrl from "valid-url";
const murmurHash3 = require("murmurhash3");

export const checkUrlOK = (url: string) => {
  if (!validUrl.isUri(url)) {
 //   console.log("Not a URI");
    return false;
  } else {
 //   console.log("Looks like an URI" + validUrl.isUri(url));
    return true;
  }
};

export const getHex32ID = (url: string) => {
  return murmurHash3.murmur32HexSync(url);
};

export const FIXED_PADDING: string = "[HASHKEY]";

export const addFixedPadding = (url: string) => {
  return url.concat(FIXED_PADDING);
};

export const removeALLPadding = (url: string) => {
  while (url.endsWith(FIXED_PADDING)) {
    url = url.replace(FIXED_PADDING, "");
  }
  return url;
};

export const SHORT_URL_LENGTH = 8;
export const PORT = 3000;

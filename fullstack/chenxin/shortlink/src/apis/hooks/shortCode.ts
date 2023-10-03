import { useContext, useConfig } from "@midwayjs/hooks";
import CryptoJS from "crypto-js";
import { ceil } from "../../utils/helper";
import bigInt from "big-integer";

/**
 * 将原始URL转换成短链Code
 * @param url 原始URL
 * @returns 
 */
export function useShortCode(url: string): Array<String> {
  const { MIX_KEY, MIN_LENGTH, MAX_LENGTH } = useConfig("SHORTURL_SERVER");
  const SHORT_CHARTS = useConfig("SHORT_CHARTS");
  const hash = CryptoJS.MD5(MIX_KEY + url);
  const hashUrl = hash.toString();
  const groupCount: number = ceil(hashUrl.length / MAX_LENGTH);

  const result = [];
  for (let i = 0; i < groupCount; i++) {
    let subStr: string = hashUrl.substring(
      i * MAX_LENGTH,
      i * MAX_LENGTH + MAX_LENGTH
    );

    const bigNum = bigInt(subStr, 16);
    let hexNum = bigNum.and(0x3fffffff);
    let charts = "";

    for (let j = 0; j < MAX_LENGTH; j++) {
      const index = hexNum.and(0x0000003d);
      charts += SHORT_CHARTS[Number(index)];

      hexNum = hexNum.shiftRight(MAX_LENGTH - 1);
    }

    result.push(charts);
  }

  return result[Math.floor(Math.random() * (result.length - 1))] || null;
}

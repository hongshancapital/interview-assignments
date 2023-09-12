import { SHORT_DNS_MAX_LENGTH, SHORT_DNS_MAX_RETRY } from '../config/env';
import { HttpError } from '../middleware/http-error';
import { RedisUtils } from '../middleware/redis';
import { HttpErrorCode } from '../types/errors';
import { LongDnsDTO, ShortDnsDTO } from '../types/short-dns';
import * as MD5 from 'ts-md5';

const chars = [
  '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
  'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
  'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
  'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
  'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
  'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
  'Y', 'Z', '-', '_',
];

const r = new RedisUtils();

export const genShortDns = async (data: LongDnsDTO, baseUrl: String): Promise<ShortDnsDTO> => {

  const hashStr = MD5.Md5.hashStr(`${ data.url }-${ data.exp }-${ new Date().getTime() }`);

  const charsLength = chars.length;
  const resultChars = [];
  for (let i = 0; i < hashStr.length; i += 2) {
    resultChars.push(chars[parseInt(`0x${hashStr[i]}${hashStr[i+1]}`, 16) % charsLength]);
  }

  let shortStr = resultChars.slice(0, SHORT_DNS_MAX_LENGTH).join('');
  let retryCount = 0;
  let isSuccess = false;
  while (retryCount < SHORT_DNS_MAX_RETRY) {
    if (await r.set(shortStr, data.url, data.exp)) {
      isSuccess = true;
      break;
    }

    retryCount ++;
    shortStr = resultChars.slice(retryCount, SHORT_DNS_MAX_LENGTH + retryCount).join('');
  }

  if (!isSuccess) {
    throw new HttpError(403, HttpErrorCode.genShortDNSFailed);
  }
  return { url: `${baseUrl}${shortStr}` };
};

export const getLongUrl = async (shortStr: string): Promise<ShortDnsDTO> => {
  const url = await r.get(shortStr);
  if (url === '') {
    throw new HttpError(403, HttpErrorCode.getLongDNSFailed);
  }
  return { url };
};

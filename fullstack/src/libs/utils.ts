import validUrl from 'valid-url';

const dict = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
export function isValidBase62Str(s: string): boolean {
  // max length 8 chars
  return /^[a-zA-Z0-9]{1,8}$/.test(s);
}

export function isValidUrl(url: string): boolean {
  return !!validUrl.isUri(url);
}
export function encodeId2Base62Str(id: number): string {
  const s: string[] = [];
  while (id) {
    s.push(dict[id % 62]);
    id = Math.floor(id / 62);
  }
  return s.reverse().join('');
}

export function decodeBase62Str2Id(s: string): number {
  let id = 0;
  for (let i = 0; i < s.length; ++i) {
    if ('a' <= s[i] && s[i] <= 'z') {
      id = id * 62 + dict.indexOf(s[i]) - dict.indexOf('a');
    }
    if ('A' <= s[i] && s[i] <= 'Z') {
      id = id * 62 + dict.indexOf(s[i]) - dict.indexOf('A') + 26;
    }
    if ('0' <= s[i] && s[i] <= '9') {
      id = id * 62 + dict.indexOf(s[i]) - dict.indexOf('0') + 52;
    }
  }
  return id;
}

export function failureResp(errMsg: string): {
  success: boolean;
  errMsg: string;
} {
  return {
    success: false,
    errMsg,
  };
}

export function successResp<T = any>(
  data: T
): {
  success: boolean;
  data: T;
} {
  return {
    success: true,
    data,
  };
}

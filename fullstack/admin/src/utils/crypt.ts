export function cryptPwd(password: string): string {
  const crypto = require('crypto');
  const md5 = crypto.createHash('md5');
  const res = md5.update(password).digest('hex')
  return res;
}
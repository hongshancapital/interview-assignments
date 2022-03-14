import * as crypto from 'crypto';

export function sha256(key: string, salt: string) {
  const val = crypto
    .createHash('SHA256')
    .update(key + salt)
    .digest('hex');
  return val.slice(0, 1);
}

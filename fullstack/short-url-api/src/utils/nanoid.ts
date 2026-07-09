import crypto from 'crypto';

export const DEFAULT_LENGTH = 8;

// https://github.com/ai/nanoid/blob/main/nanoid.js
export default function nanoid(t = DEFAULT_LENGTH) {
  return crypto
    .getRandomValues(new Uint8Array(t))
    .reduce(
      (t, e) =>
        (t += (e &= 63) < 36 ? e.toString(36) : e < 62 ? (e - 26).toString(36).toUpperCase() : e > 62 ? '-' : '_'),
      '',
    );
}

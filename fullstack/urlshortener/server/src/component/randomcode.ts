
const CHARACTERS = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_';
const LENGTH = 8;

export function generate(): string {
  let result = '';
  for (let i = 0; i < LENGTH; i++ ) {
    result += CHARACTERS.charAt(Math.trunc(Math.random() * CHARACTERS.length));
  }
  return result;
}

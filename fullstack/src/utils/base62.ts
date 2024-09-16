const CHARSET =
  '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'.split('')
const CHARSET_LEN = CHARSET.length

export function encode(num: number): string {
  if (num < 0) {
    throw new Error(`num must be a positive integer.input: ${num}`)
  }
  let res = ''
  while (num > 0) {
    res = CHARSET[num % CHARSET_LEN] + res
    num = Math.floor(num / CHARSET_LEN)
  }
  return res
}

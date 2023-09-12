import * as crypto from 'crypto'
import { customAlphabet } from 'nanoid'
const charSet = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'

function generateShortUrl(num: number) {
  const nanoid = customAlphabet(charSet, num)
  return nanoid()
}

export { generateShortUrl }

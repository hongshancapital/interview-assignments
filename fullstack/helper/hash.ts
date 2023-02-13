import crypto from 'crypto'

const hashHelpers = {
  getHashes (url: string) {
    return crypto.createHash('sha256').update(url).digest('hex').substring(0, 6);
  }
}

export default hashHelpers

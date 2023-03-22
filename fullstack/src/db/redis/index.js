const Redis = require('ioredis')

try {
  module.exports = new Redis(Config.redis)
} catch (e) {
  console.error(e)
  process.exit(1)
}

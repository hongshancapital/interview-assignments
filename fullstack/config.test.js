module.exports = {
  server: {
    host: '127.0.0.1',
    port: 3000,
  },
  redis: {
    host: '127.0.0.1',
    port: 6379,
    password: '',
    keyPrefix: 'short-url-test:',
  },
  url: {
    base: 'http://127.0.0.1:3000',
  },
}
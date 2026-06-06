export default {
  port: 3000,
  secretKey: "secret-key",
  mongo: {
    uri: "mongodb://127.0.0.1:27017/domain",
    options: {
      connectTimeoutMS: 10000,
      minPoolSize: 20,
      autoCreate: true,
    },
  },
  redis: {
    node: {
      url: "redis://127.0.0.1:6379/0",
      pingInterval: 10000
    },
    nodes: [
      {
        url: "redis://127.0.0.1.ai:6379/0"
      }
    ],
  },
  log: {
    dir: "../../logs",
  },
};

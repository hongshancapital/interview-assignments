module.exports = {
  mongodbMemoryServerOptions: {
    binary: {
      version: "4.0.9",
      skipMD5: true,
    },
    autoStart: false,
    instance: {
      dbName: "jest",
    },
  },
};

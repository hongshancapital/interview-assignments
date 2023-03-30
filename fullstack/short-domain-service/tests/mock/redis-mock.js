class MockRedisClient {
}

MockRedisClient.prototype.connect = () => { };
MockRedisClient.prototype.disconnect = () => { };
MockRedisClient.prototype.sendCommand = () => { return 1024; };

module.exports = {
    MockRedisClient,
    createClient: () => {
        return new MockRedisClient();
    }
}
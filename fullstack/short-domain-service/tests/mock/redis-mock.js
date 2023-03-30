const cache = new Map();

class MockRedisClient {
}

MockRedisClient.prototype.connect = () => { };
MockRedisClient.prototype.disconnect = () => { };
MockRedisClient.prototype.sendCommand = () => { return cache.size; };
MockRedisClient.prototype.set = (key, value) => {
    cache.set(key, value);
}
MockRedisClient.prototype.get = async (key) => {
    return await cache.get(key);
}

module.exports = {
    MockRedisClient,
    createClient: () => {
        return new MockRedisClient();
    }
}
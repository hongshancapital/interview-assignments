const cache = new Map();
let isReady = false;

class MockRedisClient {
}
MockRedisClient.prototype.connect = () => {
    isReady = true;
};
MockRedisClient.prototype.disconnect = () => { };
MockRedisClient.prototype.sendCommand = () => { return cache.size; };
MockRedisClient.prototype.set = (key, value) => {
    cache.set(key, value);
}
MockRedisClient.prototype.get = async (key) => {
    return await cache.get(key);
}
MockRedisClient.prototype.isReady = isReady;

module.exports = {
    MockRedisClient,
    createClient: () => {
        return new MockRedisClient();
    }
}
let redisDB = new Map();

class MockRedisClient {
    _isReady = false;

    get isReady() {
        return this._isReady;
    }

    connect() {
        this._isReady = true;
        return this._isReady;
    }

    disconnect() { }

    sendCommand(cmd?: string) {
        return redisDB.size;
    }

    set(key: string, value: string) {
        redisDB.set(key, value);
    }

    async get(key: string) {
        return await redisDB.get(key);
    }

    reset() {
        redisDB = new Map();
    }
}

const createClient = () => { return new MockRedisClient() }

export {
    MockRedisClient,
    createClient
}

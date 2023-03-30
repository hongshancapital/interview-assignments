const { createClient } = require('redis')

let reidsClient;

const connectToRedis = async () => {
    reidsClient = createClient();
    await reidsClient.connect();
    return reidsClient.isReady;
}

const disconnectRedis = async () => {
    await reidsClient.disconnect();
}

const getDbSize = async () => {
    return await reidsClient.sendCommand(['DBSIZE']);
}

const convert10To62 = n => {
    if (!Number.isInteger(n)) {
        return null;
    }
    if (n === 0) {
        return '0';
    }
    var digits = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    var result = '';
    while (n > 0) {
        result = digits[n % digits.length] + result;
        n = parseInt(n / digits.length, 10);
    }
    if (result.length > 8) {
        return null;
    }
    return result;
}

const getShortUri = (host, key) => {
    return `${host}/uri/${key}`;
}

module.exports = { convert10To62, connectToRedis, disconnectRedis, getDbSize, getShortUri };
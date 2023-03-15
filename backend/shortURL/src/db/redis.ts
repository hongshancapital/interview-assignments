const redis = require('redis');
const client = redis.createClient(6379, '127.0.0.1'); //配置获取略

client.on('error', (err: string) => {
    console.log(`Error ${err}`)
})

client.connect();
export default client

import * as redis from 'redis';

const redisClient = async () => {
  const client = redis.createClient({ url: 'redis://127.0.0.1:6379' });
  client.on('error', (err) => {
    console.log(`redis error: ${err}`);
  });

  client.on('connect', function() {
    console.log('redis connected success!');
  });
  await client.connect();
  return client;
}

export default redisClient;
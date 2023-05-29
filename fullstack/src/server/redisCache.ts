import { createClient } from "redis";


const client = createClient({
    url: 'redis://localhost:6379',
    /* istanbul ignore next */
    database: process.env.NODE_ENV === "test" ? 8 : 0,
});

export async function init() {
    await client.connect();
    const pong = await client.ping();
    console.log(`Redis connected: ${pong}`, process.env.NODE_ENV);
}

export async function close() {
    await client.disconnect();
}

export default client;

import dotenv from 'dotenv';
dotenv.config();

import { app, service } from './app';
import { connect, close } from './connection';

const port = process.env.PORT;

app.listen(port, async () => {
    await connect()
    service.init()
    console.log(`application listening on port ${port}`)
})

app.on('close', async () => {
    await close()
})

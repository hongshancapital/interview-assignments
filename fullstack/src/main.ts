import dotenv from 'dotenv';
dotenv.config();

import { app } from './app';
import { connect, close } from './connection';

const port = process.env.PORT;

app.listen(port, () => {
    connect()
    console.log(`application listening on port ${port}`)
})

app.on('close', async () => {
    close()
})

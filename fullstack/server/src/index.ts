import dotenv from 'dotenv';
import createApp from './app';

dotenv.config();

async function main() {
  const dbType = process.env.DB_TYPE as any
  const app = await createApp(dbType || 'memory')
  const port = process.env.PORT
  app.listen(port, () => {
    console.log(`[server] running at http://localhost:${port}`)
  })
}

main()
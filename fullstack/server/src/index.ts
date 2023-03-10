import dotenv from 'dotenv';
import createApp from './app';

dotenv.config();

async function main() {
  const app = await createApp()
  const port = process.env.PORT
  app.listen(port, () => {
    console.log(`[server] running at http://localhost:${port}`)
  })
}

main()
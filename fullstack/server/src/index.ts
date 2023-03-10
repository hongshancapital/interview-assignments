import express, { Express, Request, Response } from 'express';
import cors from 'cors'
import dotenv from 'dotenv';
import { initConnection } from './redis';
import { createService } from './service';
import { createMemoryRepository } from './repository';


dotenv.config();


async function main() {
  const app: Express = express()

  app.use(cors())

  const port = process.env.PORT

  const service = createService(createMemoryRepository())

  app.get('/long2short', async (req: Request, res: Response) => {
    const url = req.query.url as string

    let urlData = await service.createOrQueryShort(url)

    res.send({
      success: true,
      data: { short: urlData.short }
    })
  });

  app.get('/short2long', async (req: Request, res: Response) => {
    const short = req.query.short as string
    const urlData = await service.queryByShort(short)
    if (!urlData) {
      return res.send({ success: false })
    }
    res.send({
      success: true,
      data: { url: urlData.url }
    })
  })

  app.listen(port, () => {
    console.log(`[server] running at http://localhost:${port}`)
  })

}

main()
import bodyParser from 'body-parser'
import express from 'express'
import { initializeDatabase } from './components/database'
import * as shortUrlApi from './apis/shortUrl'
import { initializeBloomFilter } from './components/bloomFilter'
const app = express()
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: true }))
app.post('/api/shortUrl/create', shortUrlApi.createHandler)
app.get('/api/shortUrl/getLongUrl', shortUrlApi.getLongUrlHandler)
export async function initialize() {
  await Promise.all([initializeDatabase(), initializeBloomFilter()])
}
export default app

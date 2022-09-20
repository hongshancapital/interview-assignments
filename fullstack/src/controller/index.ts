import { Router } from 'express'
import shortUrl from './shortUrl'

const router = Router()
router.use('/shortUrl', shortUrl)

export default router
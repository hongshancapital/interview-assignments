import express from "express";
import { createShortURL, getOriginalURL } from '../controller/ShortURLController'
import { URLCheck, TOKENCHECK } from "../middleware/ShorCheck";

const router = express.Router()

router.get('/', function(req, res) {
  res.send('hello');
})

router.post('/short/create', [URLCheck], createShortURL)
router.get('/:url', [TOKENCHECK], getOriginalURL)

export default router
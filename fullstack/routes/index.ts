import express, { Router } from 'express'
import shorturlController from '../controllers/shorturlController'

const router: Router = express.Router()

router.route('/shorturl').post(shorturlController.setUrl);
router.route('/shorturl/:hash').get(shorturlController.getUrl)

export default router



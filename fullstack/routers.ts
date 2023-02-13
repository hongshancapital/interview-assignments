import express, { Router } from 'express'
import shorturlController from './controllers/shorturlController'

const routers: Router = express.Router()

routers.route('/shorturl').post(shorturlController.setUrl);
routers.route('/shorturl/:hash').get(shorturlController.getUrl)

export default routers



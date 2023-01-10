import { Router } from 'express';

import UrlController from './controllers/UrlController';
import { urlValidation, shortValidation } from './validations';

const routes = Router();
const urlController = new UrlController();

routes.post('/createShortUrl', urlValidation, urlController.createShortUrl);
routes.get('/getOriginUrl', shortValidation, urlController.getOriginUrl);

export default routes;

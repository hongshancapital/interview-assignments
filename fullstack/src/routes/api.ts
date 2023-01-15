import { Router } from 'express';
import jetValidator from 'jet-validator';

import urlRoutes from './url-routes';


// **** Init **** //

const apiRouter = Router(),
  validate = jetValidator();

// **** Setup url routes **** //

const urlRouter = Router();

// Get short url
urlRouter.post(urlRoutes.paths.getShortUrl, validate(['originalUrl', 'string','body']), urlRoutes.getShortUrl);

// Get original url
urlRouter.post(urlRoutes.paths.getOriginalUrl, validate(['shortUrl', 'string','body']), urlRoutes.getOriginalUrl);

// Add urlRouter
apiRouter.use(urlRoutes.paths.basePath, urlRouter);

// **** Export default **** //

export default apiRouter;

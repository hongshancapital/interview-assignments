import { Router } from 'express';

import urlRoutes from '@modules/urls/routes';

const routes = Router();

routes.use('/', urlRoutes);

export default routes;

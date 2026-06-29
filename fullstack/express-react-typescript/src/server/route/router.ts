import express, { Router } from 'express';
import middleware from '../middlewares';

const router: Router = express.Router();

router.use(Object.values(middleware));

export default router;

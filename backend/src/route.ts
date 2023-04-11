import { Router } from "express";
import {getOriginUrl, createShortUrl} from "./controller";
export const router:Router = Router();
router.post('/l2s', createShortUrl);
router.get('/s2l', getOriginUrl);


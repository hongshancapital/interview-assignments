import express from 'express';
import domain from './domain';

const router = express.Router();

/** /api/domainmanager/v1/domain */
router.use('/domain', domain);

export default router;

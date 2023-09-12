import express from 'express-promise-router';
import { APIRouter } from '../middleware/router-middleware';
import { api } from '../middleware/validation';
import * as shortDNS from './dns-controller';
import { ensureEmpty } from '../middleware/rest-valid';
import { LongDnsDTOSchema, DnsDTOSchema } from './dns-validation';

const router = express();
const r = new APIRouter(router);

r.route('POST', '/', api(
  { body: LongDnsDTOSchema, query: ensureEmpty, ret: DnsDTOSchema },
  shortDNS.getShortDNS
));

r.route('GET', '/:shortDns', api(
  { body: ensureEmpty, query: ensureEmpty, ret: DnsDTOSchema },
  shortDNS.getLongDNS
));

export default router;

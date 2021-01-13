import { shortUrlRoutes } from './short-url.routes';
import { shortUrlService } from './short-url.service';

export const ShortUrlModule = {
  routes: shortUrlRoutes,
  service: shortUrlService,
};

import { SL_SERVICE, SL_HOST, SL_SERVICE_PARAMS } from '../config';

import { IShortLinkService, ShortLinkParams } from './base';

import { Simple, Elasticsearch, File } from './impl';

export const getService = (): IShortLinkService => {
  const params: ShortLinkParams = {
    host: SL_HOST,
  };

  new URLSearchParams(SL_SERVICE_PARAMS).forEach((value, key) => {
    params[key] = value;
  });
  switch (SL_SERVICE) {
    case 'elasticsearch':
      return new Elasticsearch(params);
    case 'file':
      return new File(params);
    case 'simple':
    default:
      return new Simple(params);
  }
};

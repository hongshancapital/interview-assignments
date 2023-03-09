
import {Request} from 'express'
import { ParsedQs } from 'qs';
export { }

declare global {
  interface CreateShortLinkBody {
    long_link: string;
  }

  interface Result extends CreateShortLinkBody {
    short_link: string;
  }

  interface Params extends Required<Request['query']>, Required<ParsedQs> {
    shortLink?: string;
  }

}


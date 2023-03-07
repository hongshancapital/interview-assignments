
import {Request} from 'express'
import { ParsedQs } from 'qs';
export { }

declare global {
  interface CreateShortLinkBody {
    longLink: string;
  }

  interface ParseShortLinkResult extends CreateShortLinkBody {
    shortLink: string;
  }

  interface Params extends Required<Request['query']>, Required<ParsedQs> {
    shortLink?: string;
  }

  // type Params <T> = {
  //   [key in keyof T]?: 
  // }
  // type Params = {
  //   shortLink: Pick<Request['query'], string | number>
  // }
  
  // type TypeToNumber extends Pick<T, string| number> = {
  //   [key in keyof T]: string
  // }
}


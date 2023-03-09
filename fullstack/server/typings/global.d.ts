
import { Request } from 'express'
import { ParsedQs } from 'qs';
export { }

declare global {
  interface IResult {
    short_link: string;
    long_link: string;
  }

  interface IParams extends Required<Request['query']>, Required<ParsedQs> {
    shortLink?: string;
  }

  interface ITranslateFunc {
    encode: (uri: string) => string,
    decode: (uri: string) => string,
  } 

  
}


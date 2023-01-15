import {TAll} from "jet-validator";

// **** Variables **** //

// **** Types **** //

export interface IUrl {
  id: number;
  shortUrl: string; //Index
  originalUrl: string;
  urlHash?: string; //Index
  createdAt?: number;
}

// **** Functions **** //

/**
 * Get a new Url object.
 */
function _new(originalUrl: string): IUrl {
  return {
    id: -1,
    shortUrl: "",
    originalUrl,
  };
}

/**
 * Copy a url object.
 */
function copy(user: IUrl): IUrl {
  return {
    id: user.id,
    shortUrl: user.shortUrl,
    originalUrl: user.originalUrl,
  };
}

/**
 * See if an object is an instance of Url.
 */
function instanceOf(arg: TAll): boolean {
  return !!arg && typeof arg === "object" && "id" in arg && "shortUrl" in arg && "originalUrl" in arg;
}

// **** Export default **** //

export default {
  new: _new,
  copy,
  instanceOf,
} as const;

const { createShortUrlCpp } = require('bindings')('createShortUrl.node');

export async function createShortUrl(): Promise<string> {
  return createShortUrlCpp();
};


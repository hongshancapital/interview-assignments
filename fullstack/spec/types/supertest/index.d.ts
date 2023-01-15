import 'supertest';

declare module 'supertest' {

  export interface Response  {
    headers: Record<string, string[]>;
    body: {
      error: string;
      shortUrl: string;
      originalUrl: string;
    };
  }
}
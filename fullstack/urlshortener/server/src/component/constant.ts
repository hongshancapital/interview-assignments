export const APPLICATION_NAME = 'urlshortener';

export const PORT = Number(process.env.PORT || '80');

export const DATABASE_URL = process.env.DATABASE_URL || 'postgresql://dummy:dummy@localhost:5432/postgres';

export const LOG_LEVEL = process.env.LOG_LEVEL || 'debug';

export const BASE_URL = process.env.BASE_URL || `http://localhost:${PORT}`;

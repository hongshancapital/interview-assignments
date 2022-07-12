// Mapper for environment variables
export const environment = process.env.NODE_ENV || 'development';
export const port = process.env.PORT || 3000;

export const db = {
  name: process.env.DB_NAME || '',
  host: process.env.DB_HOST || '',
  port: process.env.DB_PORT || '',
  user: process.env.DB_USER || '',
  password: process.env.DB_USER_PWD || '',
};

export const logDirectory = process.env.LOG_DIR;

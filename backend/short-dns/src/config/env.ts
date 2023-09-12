import dotenv from 'dotenv';

const ENV = process.env.NODE_ENV || 'development';

// Load environment variables from .env file (.env.development, .env.test, .env.production)
dotenv.config({ path: `.env.${ENV}` });

const maxUintCheck = (initValue: string | undefined, defaultValue: number, maxValue: number): number => {
  if (initValue === undefined) {
    return defaultValue;
  }
  const value = parseInt(initValue, 10);
  if (value < 0) {
    return defaultValue;
  }
  if (value > maxValue) {
    return maxValue;
  }
  return value;
};

// Server Port
export const SERVER_PORT = process.env.SERVER_PORT || 8080;

// DB config
export const REDIS_URL = process.env.REDIS_URL;

// Short DNS Domain
export const SHORT_DNS_DOMAIN = process.env.SHORT_DNS_DOMAIN;

// Short DNS length, default 8, max 32
export const SHORT_DNS_MAX_LENGTH = maxUintCheck(process.env.SHORT_DNS_MAX_LENGTH, 8, 32);

// Gen Short DNS max retry, default 5, max 10
export const SHORT_DNS_MAX_RETRY = maxUintCheck(process.env.SHORT_DNS_MAX_RETRY, 5, 10);

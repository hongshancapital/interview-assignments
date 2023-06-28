import path from "path";
import dotenv from "dotenv";

interface Options {
  [key: string]: (value: string) => unknown;
}

export function configure<T extends Options>(options: T) {
  if (process.env.NODE_ENV !== "production") {
    dotenv.config({
      path: path.resolve(process.cwd(), ".env.dev"),
    });
  }

  const config: Record<string, unknown> = {};

  for (const [name, formatter] of Object.entries(options)) {
    const value = process.env[name];
    if (typeof value !== "string") {
      throw new Error(`env.${name} not set`);
    }
    config[name] = formatter(value) as T[keyof T];
  }

  return config as {
    [K in keyof T]: ReturnType<T[K]>;
  };
}

export const config = configure({
  PORT: Number,
  NODE_ID: Number,
  ID_MONGODB_URI: String,
  PERSIST_MONGODB_URI: String,
  CACHE_REDIS_URI: String,
  TTL: Number,
});

const path = require('path');
const dotenv = require('dotenv');
const { z } = require('zod');

module.exports.setupEnv = () => {
  const dotenvConfigOutput = dotenv.config({ path: path.resolve(__dirname, '.env.test') });
  if (dotenvConfigOutput.error) {
    throw dotenvConfigOutput.error;
  }

  const envSchema = z
    .object({
      DATABASE_USER: z.string(),
      DATABASE_PASSWORD: z.string(),
      DATABASE_DB: z.string(),
      DATABASE_PORT: z.string(),
    })
    .strict();

  const env = envSchema.parse(dotenvConfigOutput.parsed);

  process.env.DATABASE_URL = `postgresql://${env.DATABASE_USER}:${env.DATABASE_PASSWORD}@localhost:${env.DATABASE_PORT}/${env.DATABASE_DB}`;
  process.env.ENV_FILE = path.resolve(__dirname, '.env.test');
};

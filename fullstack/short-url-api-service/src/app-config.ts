import dotenv from "dotenv"
import path from "path"

type EnvType = "dev" | "staging" | "prod"

const ENV: EnvType =
    (process.env.CREDIT_CODE_API_SERVICE_ENV as EnvType) ?? "prod"

const envPath = path.resolve(__dirname, `../envs/.${ENV}.env`)

dotenv.config({
    path: envPath,
})

const processEnv = process.env

const isProduction = (ENV === 'prod')

export { ENV, processEnv, isProduction }

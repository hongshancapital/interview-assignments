import { cleanEnv } from "envalid";
import { port, str, bool, num } from "envalid/dist/validators";

export default cleanEnv(process.env, {
    MONGO_CONNECTION_STRING: str(),
    LOG_PATH: str(),
    PORT: port(),
    HOST_HTTPS: bool(),
    HOST_DOMAIN: str(),
    RATE_LIMIT_URL_GET: num(),
    RATE_LIMIT_URL_GET_WINDOW_SEC: num(),
    RATE_LIMIT_URL_POST: num(),
    RATE_LIMIT_URL_POST_WINDOW_SEC: num(),
});
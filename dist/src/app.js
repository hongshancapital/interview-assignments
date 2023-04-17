"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const koa_1 = __importDefault(require("koa"));
const config_1 = __importDefault(require("config"));
const middlewares_1 = __importDefault(require("./middlewares"));
const routes_1 = __importDefault(require("./routes"));
const databases_1 = __importDefault(require("./databases"));
const app = new koa_1.default();
app.use(middlewares_1.default());
app.use(routes_1.default());
async function main() {
    const displayColors = config_1.default.get('displayColors');
    try {
        const dbUrl = config_1.default.get('dbUrl');
        await databases_1.default(dbUrl);
        console.info(displayColors ? '\x1b[32m%s\x1b[0m' : '%s', `Connected to ${dbUrl}`);
    }
    catch (error) {
        console.error(displayColors ? '\x1b[31m%s\x1b[0m' : '%s', error.toString());
    }
    try {
        const port = config_1.default.get('port');
        const server = app.listen(port);
        console.info(displayColors ? '\x1b[32m%s\x1b[0m' : '%s', `Listening to http://localhost:${port}`);
    }
    catch (err) {
        console.error(displayColors ? '\x1b[31m%s\x1b[0m' : '%s', err);
    }
}
main();
//# sourceMappingURL=app.js.map
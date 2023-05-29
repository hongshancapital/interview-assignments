import express from "express";
import bodyParser from "body-parser";
import * as db from "./db";
import {bindRoutes, defaultErrorHandler} from "./routes";
import * as redis from "./redisCache";


const app = express();
const port = 8080; // default port to listen

async function main() {
    if (process.env.NODE_ENV !== "production") {
        app.use(express.static('dist/frontend'));
    }

    app.use(bodyParser.json());

    bindRoutes(app);

    app.use(defaultErrorHandler);

    await db.init();
    await redis.init();

    app.listen(port, () => {
        console.log(`server started at http://localhost:${ port }`);
    });
}

main().catch(e => console.error(e));

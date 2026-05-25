import morgan from "morgan";
import app from "./app";
import { logger } from "./util/log";
import env from "./util/validateEnv";
import mongoose from "mongoose";
import * as rfs from 'rotating-file-stream';

const webLogger = () => {
    const stream = rfs.createStream("web_request.log", {
        size: "20M", // rotate every 10 MegaBytes written
        interval: "1d", // rotate daily
        path: env.LOG_PATH,
    });

    return morgan('combined', { stream: stream });
}

mongoose.connect(env.MONGO_CONNECTION_STRING).then(() => {
    logger.info("Mongoose connected");
});

app.use(webLogger());

const server = app.listen(env.PORT, () => {
    logger.info("Server running on port: " + env.PORT);
});


// 监听应用关闭事件
process.on('SIGINT', () => {
    // 关闭 Express 服务器
    server.close(() => {
        logger.info('Server is closed');

        // 关闭数据库连接
        mongoose.disconnect().then(() => {
            logger.info('Mongoose closed');
            process.exit(0); // 退出 Node.js 进程
        });
    });
});
"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const morgan_1 = __importDefault(require("morgan"));
const connection_1 = require("./db/connection");
const shortUrlController_1 = require("./controller/shortUrlController");
require("express-async-errors");
const fs_1 = __importDefault(require("fs"));
const path_1 = __importDefault(require("path"));
const logger_1 = __importDefault(require("./utils/logger"));
const port = 3000;
const app = (0, express_1.default)();
app.use(express_1.default.json());
const logStream = fs_1.default.createWriteStream(path_1.default.join(__dirname, '../logs', 'access.log'));
app.use((0, morgan_1.default)('combined', { stream: logStream }));
app.post('/generate', shortUrlController_1.generateShortUrl);
app.get('/geturl', shortUrlController_1.responseShortUrl);
app.use(function (err, req, res, next) {
    res.status(500);
    res.json({ error: err.message });
    next(err);
});
(0, connection_1.initDatabase)().then(() => {
    logger_1.default.info('数据库初始化成功。');
    app.listen(port, () => {
        logger_1.default.info(`服务已启动，监听端口为 ${port}`);
    });
}).catch(err => {
    logger_1.default.error('服务启动失败。数据库初始化失败:', err);
});

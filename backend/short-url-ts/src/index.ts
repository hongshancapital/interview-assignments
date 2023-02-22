import {connectMasterDb, closeDbConnection} from './db/PostgreSql';
import {connectRedis, closeRedis} from './db/redis';
import {startServer, stopServer} from "./web_server";

(async () => {

    // connect pgsql
    await connectMasterDb();

    // connect redis
    await connectRedis();

    // start server
    startServer();

    // listen graceful shutdown
    listenShutDown();

})().catch((err: Error) => {
    console.error(err);
    console.error('系统启动失败T_T 错误见上');
    process.exit(0);
});

function listenShutDown(): void {
    const ShutdownSignals = ['SIGINT', 'SIGTERM'];

    ShutdownSignals.forEach((signal) => {
        process.on(signal, async function () {
            console.log(`-----[${signal}] signal received. begin graceful shutdown-----`);
            await shutdown().catch(err => {
                err.message = `graceful shutdown failed, force shutdown now. detail: ${err.message}`;
                console.error(err);
                // 直接退出
                process.exit(1);
            });
        });
    });
}

let startClose: boolean = false;

async function shutdown(): Promise<void> {
    if (startClose) {
        console.log(`[signal] repeat signal.`);
        return;
    }
    startClose = true;

    // 30s 未完成则直接退出
    const Timeout = 30 * 1000;
    setTimeout(() => {
        console.warn(`[${Timeout}ms]timeout, force shutdown now.`);
        process.exit(1);
    }, Timeout);

    // http stop
    await stopServer();
    // pgsql db stop
    await closeDbConnection();
    // redis stop
    await closeRedis();

    // 结束
    console.log(`finished, bye.`);
    process.exit(0);
}
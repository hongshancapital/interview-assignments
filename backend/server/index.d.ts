import 'egg';

declare module 'egg' {
    interface Application {
        redisPrefix: string,
        validator: any,
        errorCode: {
            Parameter: number, // 参数错误
            SystemInvalid: number, // 系统错误
        },
    }
}

declare module 'egg-redis' {
    interface ClusterOptions {
        weakDependent?: boolean
    }
}

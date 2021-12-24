export declare class RedisService {
    client: any;
    connected: boolean;
    constructor();
    get(key: any): Promise<any>;
    set(key: any, value: any, expireS: any): Promise<unknown>;
}

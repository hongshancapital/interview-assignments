/**
 * db interface
*/
interface IMongoDB {
    url: string;
    port: number;
    collection?: string;
    options?: object;
}

export interface IConfig {
    mongodb: IMongoDB,
}

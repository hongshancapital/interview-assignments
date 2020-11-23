import { Shorten } from './entity/shorten';
export declare class AppService {
    constructor();
    shortenerLink(body: any): Promise<Shorten>;
    retrieveShortened(id: any): Promise<string>;
    trackShortened(id: any, req: any): Promise<void>;
}

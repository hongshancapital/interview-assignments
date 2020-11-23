import { AppService } from './app.service';
import { Shorten } from './entity/shorten';
import { ShortenDto } from './dto/ShortenDto';
export declare class AppController {
    private readonly appService;
    constructor(appService: AppService);
    getShortened(shortenDto: ShortenDto): Promise<Shorten>;
    getLink(params: any, req: any): Promise<string>;
}

import { NestMiddleware } from '@nestjs/common';
import { Response, Request } from 'express';
import { BloomFilterService } from '../services/bloomFilter.service';
export declare class filterMiddleware implements NestMiddleware {
    private readonly bloomFilterService;
    constructor(bloomFilterService: BloomFilterService);
    use(req: Request, res: Response, next: Function): Promise<void>;
}

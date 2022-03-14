import { Context } from '@midwayjs/koa';
import { UserService } from '../service/user.service';
import { UrlModel } from '../entity/url';
export declare class APIController {
    ctx: Context;
    userService: UserService;
    getUser(aa: any): Promise<{
        success: boolean;
        message: string;
        data: UrlModel[];
    }>;
}

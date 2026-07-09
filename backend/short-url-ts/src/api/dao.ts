import {UrlModel, UrlInstance} from '../entity/urlModel';
import * as Util from '../util';

export class UrlDao {
    constructor() {
    }

    async creatUrl(longUrl: string): Promise<UrlInstance> {
        if (!longUrl) {
            Util.throwErr('500_missing_param', '缺少必有参数[longUrl]', 'ER230217141332');
        }
        return await UrlModel.create({
            'origin': longUrl
        });
    }

    async getLongUrl(id: number): Promise<null | string> {
        const url = await UrlModel.findOne({
            attributes: ['origin'],
            where: {
                'id': id,
                'is_deleted': false
            }
        });
        return url && url.origin;
    };

    async getId(longUrl: string): Promise<null | number> {
        const url = await UrlModel.findOne({
            attributes: ['id'],
            where: {
                'origin': longUrl,
                'is_deleted': false
            }
        });
        return url && url.id;
    };
}

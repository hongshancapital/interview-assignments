import { Service } from 'egg';
import * as crypto from 'crypto';

export default class ApplicationService extends Service{
    public async encodeUrl(url: string): Promise<string> {
        let id = await this.getId(url);
        if (!id) {
            id = await this.createId(url);
            const ip = this.getIp();
            const ua = this.getUa();
            await this.ctx.model.Url.create({shortId: id, ip, url, ua});
        }
        const shortUrl = this.getUri() + id;
        return shortUrl;
    }

    public async decodeUrl(id: string) {
        const result = await this.ctx.model.Url.findOne({where: {shortId: id}});
        return result ? result.url : '';
    }

    /**
     * 获取url对应的id
     * @param url 
     * @returns 
     */
    private async getId(url: string) {
        const result = await this.ctx.model.Url.findOne({
            where: {
                url
            },
            attributes: ['shortId']
        });
        return result?.shortId;
    }

    /**
     * 创建url的唯一映射id
     * @param url 
     * @param size 
     * @returns 
     */
    private async createId(url: string, size = 4) {
        const md5sum = crypto.createHash('md5');
        md5sum.update(url);
        const encodeUrl = md5sum.digest('hex');
        let id = '';
        for (let i = 0 ; i < size; i++) {
            const randIndex = Math.floor(Math.random() * encodeUrl.length);
            id += encodeUrl[randIndex];
        }
        const hasId = await this.hasId(id);
        if (hasId) {
            this.createId(url);
        }
        return id;
    }

    /**
     * 检查id是否重复
     * @param id 
     * @returns 
     */
    private async hasId(id: string) {
        const result = await this.ctx.model.Url.findOne({
            where: {
                shortId: id,
            },
            attributes: ['id'],
        });
        return !!result;
    }


    /**
     * 获取短域名服务器api
     * @returns 
     */
    private getUri() {
        return 'http://' + this.ctx.header['host'] + '/';
    }

    /**
     * 获取用户ip
     * @returns
     */
    private getIp() {
        const {ctx} = this;
        const ip = ctx.request.ip ||
                ctx.request.socket.remoteAddress || '0.0.0.0';
        return ip;
    }

    // 获取用户 UserAgent
    private getUa() {
        return this.ctx.header['user-agent'] || 'N/A';
    }
}

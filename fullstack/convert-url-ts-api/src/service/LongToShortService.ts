import ConvertUrl from "../dto/model/convertUrlModel"
import logger from "../logger";
import  md5 from 'md5';
import { QueryTypes } from "sequelize";
class LongToShort{
    async getAll(){
        let data = await ConvertUrl.findAll();
        logger.info(data)
         return data ;
    }
    async convertToShort(longUrl:string):Promise<string>{
        let md5url =  md5(longUrl);
        let row:ConvertUrl|null = await ConvertUrl.findOne({
            where:{
                urlLongMd5:md5url,
                delFlag:0
            }
        });
        if(row){
            return row.urlShort;
        }
        let reg = /[a-zA-Z]+:[/][/][a-zA-Z._-]+[/]/
        let domain:RegExpMatchArray|null= longUrl.match(reg);
        let shortUrl = await this.getSHortUri(domain?.toString() as string);
        let insertData:ConvertUrl = new ConvertUrl();
        insertData.urlLong = longUrl;
        insertData.urlLongMd5 = md5url;
        insertData.urlShort = shortUrl as unknown as string;
        await insertData.save();
        return insertData.urlShort;
    }
    private async getSHortUri(domain:string):Promise<string|null> {
        // TODO 若同一域名超出范围后续可以考虑改成其他生成规则
        let shortUri:any = await ConvertUrl.sequelize?.query('SELECT substring(md5(rand()),1,8) as shortUri',{type:QueryTypes.SELECT});
        if(!shortUri || shortUri.length === 0){
            return null;
        }
        let convertUrl:ConvertUrl|null = await ConvertUrl.findOne({
            where:{
                urlShort:domain+shortUri[0].shortUri,
                delFlag: 0
            }
        });
        if(convertUrl){
            return this.getSHortUri(domain);
        }
        return domain + shortUri[0].shortUri;
    }
}
export default new LongToShort
import ConvertUrl from "../dto/model/convertUrlModel"
import { MyError } from "../util/MyError";
class ShortToLong{
    async convertToLong(shortUrl:string):Promise<string>{
        let row:ConvertUrl|null = await ConvertUrl.findOne({
            where:{
                urlShort:shortUrl,
                delFlag:0
            }
        });
        if(row){
            return row.urlLong;
        }
        throw new MyError('该短域名无效，请先调用/convert/longToShort接口生成短域名');
    }
}
export default new ShortToLong
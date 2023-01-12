import 'reflect-metadata';
import { Service } from "typedi";
import UrlRepository from '../repositories/UrlRepository';
import StrTool from '../tools/StrTool';

@Service()
class ApiService {
    constructor(private readonly urlRep: UrlRepository) {

    }
    async long2short(longUrl: string) {
        let md5 = StrTool.getMd5(longUrl);
        let exists= await this.urlRep.exists(md5);
        if(exists>0){
            return this.urlRep.get(md5);
        }
        else{
            let data =await this.urlRep.addUrlData(longUrl);
            return data.short;
        }
    }
    async short2long(shortUrl: string) {
        let exists=await this.urlRep.exists(shortUrl);
        if(exists>0){
            return await this.urlRep.get(shortUrl);
        }
        else{
            return null;
        }
    }
}

export default ApiService;


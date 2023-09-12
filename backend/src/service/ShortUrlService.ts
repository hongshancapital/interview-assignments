import model from '../model/ShortUrl';
let {Document} = require("mongoose");

class ShortUrlService {
    public model;
    public constructor(){
        this.model = model;
    }

    public async getLongUrlByShortId(shortId:string) :Promise<Document|null>{
        return this.model.findOne({shortId});
    }

    public async getShortIdByLongUrl(longUrl:string) :Promise<Document|null>{
        return this.model.findOne({longUrl});
    }

    public saveUrl(longUrl:string,shortId:string) :Promise<Document|null>{
        return this.model.findOneAndUpdate({shortId},{longUrl,createAt:new Date()},{new:true,upsert:true});
    }

    public addShortUrl(shortId:string) :Promise<Document|null>{
        return this.model.create({shortId});
    }

}


export default ShortUrlService
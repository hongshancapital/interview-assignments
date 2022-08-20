
/**
 * Url实体
 */

import StrTool from "../tools/StrTool";

class UrlEntity{
    
    
    private _long:string;
    private _id:number;
    
    private _md5:string;
    private _short:string;
    /**
     * @param url{string} 长地址
     * @param id{number} 数字id
     */
    constructor(url:string,id:number){
        this._long=url;
        this._id=id;
    }

    public get long(){
        return this._long;
    }
    public get id(){
        return this._id;
    }
    public get short(){
        if(this._id){
            this._short=StrTool.convert64Base(BigInt(this._id));
        }
        return this._short;
    }
    public get md5(){
        if(this._long){
            this._md5=StrTool.getMd5(this._long);
        }
        return this._md5;
    }
}
export default UrlEntity;
"use strict";
import { shortUrlKey } from "../const";

export default class DB {
  static idTable: any;
  static urlTable: any;
  
  static getOriginUrl(key: string): Promise<string> {
    return DB.urlTable.get(key);
  }
  static getCurrentId(): Promise<number> {
    return DB.idTable.get(shortUrlKey);
  }
  static setUrl(key: string, value: any): Promise<boolean> {
    return DB.urlTable.put(key, value);
  }
  static setId(value: number): Promise<boolean> {
    return DB.idTable.put(shortUrlKey, value);
  }
 
  
}
/*
let idDB = level("/data/id", { valueEncoding: "json" });
let urlDB = level("/data/url", { valueEncoding: "json" });
// let hostDB = level("/data/host", { valueEncoding: "json" });

const getOriginUrl: getOriginUrl = (key:string):Promise<string>=>{
  return urlDB.get(key);
}

// const getHost: getHost = (key:string):Promise<string>=>{
//   return hostDB.get(key);
// }

const getCurrentId: getCurrentId = (key:string):Promise<number>=>{
  return idDB.get(key);
}

const setUrl: setUrl = (key:string,value:any):Promise<boolean>=>{
  return urlDB.put(key,value);
}

const setId: set<number> = (key:string,value:any):Promise<boolean>=>{
  return idDB.put(key,value);
}
export default {setId,setUrl,getOriginUrl,getCurrentId}

*/

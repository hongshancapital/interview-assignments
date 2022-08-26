
import { ExpressServer } from "./express"
import db from "../db"
import {Server} from 'http'
export default class Boot{
    public static async run():Promise<Server>{
        let server:Server = new ExpressServer().init();
        try{
            await db()
        }catch(exception){
            console.error('db init is error!')
        }
        return server;
    }
}

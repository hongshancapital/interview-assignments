/*
    业务逻辑实体
*/
import { exec } from './db/mysql'

export interface IShortDomain{
    id: number
    shortKey: string
    url: string
}


export class ShortDomain{
    constructor(){}
    /**
    * 转换长链接并插入数据库.
    * @param {string} url - 长url
    */
    async insert(url: string): Promise<string>{
        const res:{insertId:number} = await exec<{insertId:number}>(`INSERT INTO site_map (shortKey, url) VALUES ('wait', '${url}')`)
        const shortKey = this.idToShortKey(res.insertId)
        await exec(`UPDATE site_map SET shortKey='${shortKey}' WHERE id=${res.insertId}`)
        return shortKey
    }
    /**
    * 根据短链接查询长链接
    * @param {string} shortKey - 长url
    */
    async query(shortKey: string):Promise<IShortDomain | undefined>{
        const res:IShortDomain[] = await exec<IShortDomain[]>(`SELECT * FROM site_map WHERE shortKey='${shortKey}'`)
        if(!res || res.length === 0)return undefined
        return res[0]
    }
    /**
    * 转换长链接并插入数据库.
    * @param {string} url - 长url
    */
    private idToShortKey(id: number): string{
        const codes = 'dBX7PVGR6kvbf3euQyscJ0zI9FWA1MrONwSomh28pn5xCg4jEHlqKtYLiUTaDZ'
        let shortKey = ''

        while(id > 0){
            const index = id % 62
            shortKey += codes[index]
            id = Math.floor(id/62)
        }

        return shortKey
    }
}

export default new ShortDomain()
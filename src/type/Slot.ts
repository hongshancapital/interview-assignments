import { MemorySlotRepos } from '../repos/MemorySlotRepos'
import { DataNode } from './DataNode'
import { Md5 } from 'ts-md5'
class Slot{
    MAPPING:string = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ+-";

    codeLength:number;

    slotName:string;

    preFetchOffsetSize:number;

    slotRepos:MemorySlotRepos;

    maxAvailableOffset:number;

    currentOffset:number; 

    constructor(slotName:string, codeLength:number, preFetchOffsetSize:number, slotRepos:MemorySlotRepos) { 
        this.slotName = slotName; 
        this.codeLength = codeLength; 
        this.preFetchOffsetSize = preFetchOffsetSize; 
        this.slotRepos = slotRepos; 
        this.maxAvailableOffset = 0;
        this.currentOffset = 0;
    }

    public init():Promise<Slot> {
        return this.preFetchOffset();
    }

    private async preFetchOffset():Promise<Slot>  {
        let mao = this.slotRepos.prefetchOffset(this.slotName, this.preFetchOffsetSize);
        const result = await mao;
        this.maxAvailableOffset = result;
        this.currentOffset = this.maxAvailableOffset - this.preFetchOffsetSize;
        return this;
    }

    private nextOffset():number {
        let nextOffset:number = this.currentOffset++;
        if (this.currentOffset == this.maxAvailableOffset - this.preFetchOffsetSize / 4) {
            this.preFetchOffset().then(() => {});
        }
        return nextOffset;
    }

    private mapping(offset:number):string {
        let divider:number = this.MAPPING.length;
        let dividend:number = offset;
        let sb:string = '';
        let remainder = dividend % divider
        let quotient = Math.floor(dividend / divider)
        while (quotient > 1) {
            let sb1 = this.MAPPING.charAt(remainder);
            sb = sb.concat(sb1);
            remainder = quotient % divider
            quotient = Math.floor(quotient / divider)
        }
        while (sb.length < this.codeLength) {
            sb = sb.concat(this.MAPPING.charAt(0));
        }
        let reverseSb = "";
        for (let char of sb) {
            reverseSb = char + reverseSb;
        }
        let shortUrl:string = this.slotName + reverseSb;
        return shortUrl;
    }

    public nextNode(origin:string):Promise<DataNode> {
        let offset:number = this.nextOffset();
        let shortUrl:string = this.mapping(offset);
        let md5:string = Md5.hashStr(origin);
        let node:DataNode = new DataNode(this.slotName, offset, origin, shortUrl, md5);
        return this.slotRepos.insertNode(node);
    }

    public getNodeByOrigin(origin:string, cb:(err:any, data:DataNode)=>void):void {
        this.slotRepos.getNodeByOrigin(this.slotName, origin, (err, data) => {
            if (data != null) {
                cb(err, data);
            } else {
                this.nextNode(origin).then((data) => {
                    cb(null, data);
                })
            }
        });
    }

    public getNodeByCode(code:string, cb:(err:any, data:DataNode)=>void):void {
        this.slotRepos.getNodeByCode(this.slotName, code, cb);
    }
}

export {Slot}
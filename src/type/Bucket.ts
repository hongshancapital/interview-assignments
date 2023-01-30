import { Slot } from './Slot'
import { Md5 } from 'ts-md5'
import { DataNode } from './DataNode';
import { MemorySlotRepos } from '../repos/MemorySlotRepos'

class Bucket{
    SLOT_NAME_LENGTH:number = 2;

    CODE_LENGTH:number = 6;

    slotContainer:Map<string, Slot>;

    slotRepos:MemorySlotRepos;

    preFetchOffsetSize:number;

    constructor(preFetchOffsetSize:number, slotRepos:MemorySlotRepos) { 
        this.preFetchOffsetSize = preFetchOffsetSize; 
        this.slotRepos = slotRepos; 
        this.slotContainer = new Map();
    }

    public getByCode(code:string, cb:(err:string, data:DataNode)=>void):void {
        let slotName:string = code.substring(0, 2);

        let slot = this.slotContainer.get(slotName);
        if (typeof slot !== 'undefined') {
            slot.getNodeByCode(code, cb);
            return;
        }

        slot = new Slot(slotName, this.CODE_LENGTH, this.preFetchOffsetSize, this.slotRepos);
        slot.init().then((result) => {            
            this.slotContainer.set(slotName, result);
            result.getNodeByCode(code, cb);
        })
    }

    public getByOrigin(origin:string, cb:(err:string, data:DataNode)=>void):void {
        let md5:string = Md5.hashStr(origin);
        let slotName:string = md5.substring(0, this.SLOT_NAME_LENGTH);

        let slot = this.slotContainer.get(slotName);
        if (typeof slot !== 'undefined') {
            slot.getNodeByOrigin(origin, cb);
            return;
        }

        slot = new Slot(slotName, this.CODE_LENGTH, this.preFetchOffsetSize, this.slotRepos);
        slot.init().then((result) => {            
            this.slotContainer.set(slotName, result);
            result.getNodeByOrigin(origin, cb);
        })
    }
}

export {Bucket}
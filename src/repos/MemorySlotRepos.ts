import { DataNode } from "../type/DataNode";
import { MysqlSlotRepos } from "./MysqlSlotRepos"

class MemorySlotRepos {

    slotSize:number;

    map:Map<string, SlotData>;

    persistencedRepos:MysqlSlotRepos;

    constructor(slotSize:number, persistencedRepos:MysqlSlotRepos) { 
        this.slotSize = slotSize; 
        this.map = new Map();
        this.persistencedRepos = persistencedRepos;
    }

    private getSlotData(slotName:string):SlotData {
        let slotData = this.map.get(slotName);
        if (typeof slotData !== 'undefined') {
            return slotData;
        }
        slotData = new SlotData(this.slotSize);
        this.map.set(slotName, slotData);
        return slotData;
    }

    public async prefetchOffset(slotName: string, preFetchOffsetSize: number):Promise<number> {
        return this.persistencedRepos.prefetchOffset(slotName, preFetchOffsetSize);
    }

    public async insertNode(dataNode: DataNode):Promise<DataNode> {
        return this.persistencedRepos.insertNode(dataNode);
    }

    public getNodeByOrigin(slotName: string, origin: string, cb:(err:any, data:DataNode)=>void):void {
        let slotData:SlotData = this.getSlotData(slotName);
        let dataNode = slotData.getNodeByOrigin(origin);
        if (typeof dataNode !== 'undefined' && dataNode != null) {
            cb(null, dataNode);
            return;
        }
        let data = this.persistencedRepos.getNodeByOrigin(slotName, origin);
        data.then((node) => {
            if (node != null) {
                slotData.insertNode(node);
            }
            cb(null, node);
            return;
        });
    }

    public getNodeByCode(slotName: string, code: string, cb:(err:any, data:DataNode)=>void):void {
        let slotData = this.getSlotData(slotName);
        let dataNode = slotData.getNodeByCode(code);
        if (typeof dataNode !== 'undefined' && dataNode != null) {
            cb(null, dataNode);
            return;
        }
        let data = this.persistencedRepos.getNodeByCode(slotName, code);
        data.then((node) => {
            if (node != null) {
                slotData.insertNode(node);
            }
            cb(null, node);
            return;
        });
    }
}

class SlotData {
    originMap:Map<string, DataNode>;
    codeMap:Map<string, DataNode>;
    currentOffset:number;
    size:number;
    usedOffset:number;

    constructor(size:number) {
        this.originMap = new Map();
        this.codeMap = new Map();
        this.currentOffset = 0;
        this.usedOffset = 0;
        this.size = size;
    }

    public prefetchOffset(preFetchOffsetSize:number):number {
        this.currentOffset = this.currentOffset + preFetchOffsetSize;
        return this.currentOffset;
    }

    public insertNode(dataNode:DataNode):void{
        this.codeMap.set(dataNode.code, dataNode);
        this.originMap.set(dataNode.origin, dataNode);
        this.usedOffset++;
    }

    public getNodeByOrigin(origin:string):DataNode | null {
        let dataNode = this.originMap.get(origin);
        if (typeof dataNode !== 'undefined') {
            return dataNode;
        }
        return null;
    }

    public getNodeByCode(code:string):DataNode | null {
        let dataNode = this.codeMap.get(code);
        if (typeof dataNode !== 'undefined') {
            return dataNode;
        }
        return null;
    }

}
export {MemorySlotRepos}
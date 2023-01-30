class DataNode {
    slotName:string;

    offset:number;

    origin:string;

    code:string;

    md5:string;

    constructor(slotName:string, offset:number, origin:string, code:string, md5:string) { 
        this.slotName = slotName; 
        this.offset = offset; 
        this.origin = origin; 
        this.code = code; 
        this.md5 = md5;
    }
}
export {DataNode}
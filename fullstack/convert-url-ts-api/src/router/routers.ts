export enum Method {
    POST="post",
    GET="get"
}
export class Router{
    private requestMapping:string;
    private name:string;
    private method:Method;
    constructor(requestMapping:string,name:string,method:Method) {
        this.requestMapping = requestMapping;
        this.name = name;
        this.method = method;
    }
    public getRequestMapping():string{
        return this.requestMapping;
    }
    public setRequestMapping(requestMapping:string):void{
        this.requestMapping = requestMapping;
    }
    public getName():string{
        return this.name;
    }
    public setName(name:string):void{
        this.name =  name;
    }
    public getMethod():Method{
        return this.method;
    }
    public setMethod(method:Method){
        return this.method = method;
    }
}
export class Routers{
    private title:string;
    private groupName:string;
    private root:string;
    private routerList:Router[];
    constructor(title:string,groupName:string,root:string,routerList:Router[]) {
        this.title = title;
        this.groupName  = groupName;
        this.root = root;
        this.routerList = routerList;
    }
    public getTitle():string{
        return this.title;
    }
    public getGroupName():string{
        return this.groupName;
    }
    public getRoot():string{
        return this.root;
    }
    public getRouterList():Router[]{
        return this.routerList
    }
    public setTitle(title:string):void{
        this.title = title;
    }
    public setGroupName(groupName:string):void{
        this.groupName = groupName;
    }
    public setRoot(root:string):void{
        this.root = root;
    }
    public setRouterList(routerList:Router[]):void{
        this.routerList = routerList;
    }
}
export class RouterFactory{
    private static ROUTERS:Routers[];
    private static isInited:boolean = false;
    public getRouters():Routers[]{
        if(RouterFactory.isInited) {
            return RouterFactory.ROUTERS;
        }else{
            console.error("Routers is not init.")
            return [];
        }
    }
    public init(){
        if(!RouterFactory.ROUTERS){
            RouterFactory.ROUTERS = [];
        }
        let longToShort = new Router('/longToShort','longToShort',Method.POST);
        let shortToLong = new Router('/shortToLong','shortToLong',Method.POST);
        let urlConvert:Routers = new Routers('长短连接转换API接口集合','urlConvert','/convert',[longToShort,shortToLong]);
        RouterFactory.ROUTERS.push(urlConvert)
        RouterFactory.isInited = true;
    }
}
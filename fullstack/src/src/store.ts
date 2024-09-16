import { MemStore } from './storeMem';


interface IStore {
    save(key: string, value: any): void;
    load(key: string): any;
    delete(key: string): void;
    has(key: string): boolean;
}

const storeInstance: IStore = new MemStore();


export {
    IStore,
    storeInstance
}
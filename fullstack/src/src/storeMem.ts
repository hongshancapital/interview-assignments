import { IStore } from './store';
import { BloomFilter } from './bloomFilter';

interface MemMap {
    [key: string]: any
}

var memMap: MemMap = {};
const bf = new BloomFilter(parseInt(process.env.BF_SIZE || '100000000'), parseFloat(process.env.BF_FPP || '0.01'));

export class MemStore implements IStore {
    save(key: string, value: any): void {
        memMap[key] = value;
        bf.add(key);
    }
    load(key: string): any {
        return memMap[key];
    }
    delete(key: string): void {
        delete memMap[key];
    }
    has(key: string): boolean {
        return bf.has(key);
        // return memMap.hasOwnProperty(key);
    }
}
import { MemStore } from '../src/storeMem';

describe('MemStore', () => {
    it('MemStore defined', () => {
        expect(MemStore).toBeDefined();
    })

})

describe('MemStore method', () => {
    const storeInstance = new MemStore();
    it('has', () => {
        expect(storeInstance.has('test')).toBe(false);
    })
    it('load', () => {
        expect(storeInstance.load('test')).toBe(undefined);
    })
    it('save', () => {
        storeInstance.save('test', 'test')
        expect(storeInstance.load('test')).toBe('test');
        expect(storeInstance.has('test')).toBe(true);
    })
    it('delete', () => {
        storeInstance.delete('test')
        expect(storeInstance.load('test')).toBe(undefined);
    })
})
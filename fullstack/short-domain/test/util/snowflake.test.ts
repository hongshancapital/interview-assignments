/**
 * 工具方法单元测试
 */
import { Snowflake } from "../../src/utils/snowflake/snowflake";

describe('test basic snowflake', () => {
    const gen = new Snowflake({
        workerId: 1,
        seqBitLength: 3,
        workerIdBitLength: 3
    });

    it('test next id', () => {
        const nextID = gen.nextId();
        expect(nextID).toBeGreaterThan(+new Date);
        expect(nextID).toBeLessThan(Math.pow(2, 47));
    })

    it('test next number', () => {
        const nextNumber = gen.nextNumber();
        expect(nextNumber).toBeGreaterThan(+new Date);
        expect(nextNumber).toBeLessThan(Math.pow(2, 47));
    })

    it('test next bigInt', () => {
        const nextBigInt = gen.nextBigId();
        expect(nextBigInt).toBeGreaterThan(+new Date);
        expect(nextBigInt).toBeLessThan(Math.pow(2, 47));
    })

    it('test next 62Id', () => {
        const next62Id = gen.next62Id();
        expect(next62Id.length).toBe(8);
    })
})

import { SnowflakeIdBit48 } from './id.snowflake.bit48';

describe('SnowflakeIdBit48', () => {
  const idGenerator = new SnowflakeIdBit48({ workerId: 3 });
  const times = 10000;
  it(`${times} ids should be unique with workerId `, async () => {
    const ids = [];
    for (let i = 0; i < times; i++) {
      const id = idGenerator.nextBigId();
      const unique = !ids.includes(id);
      expect(unique).toBeTruthy();
      ids.push(id);
    }
  });

  const rate = 3000;
  it(`${rate} ids generated in 1s`, () => {
    const startTime = BigInt(Date.now());
    for (let i = 0; i < rate; i++) {
      idGenerator.nextBigId();
    }
    const endTime = BigInt(Date.now());
    const useTime = endTime - startTime;
    console.log(`1000 ids generated in ${useTime} ms`);
    expect(useTime).toBeLessThan(rate);
  });
});

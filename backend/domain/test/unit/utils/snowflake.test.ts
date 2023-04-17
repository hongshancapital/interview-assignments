import { Snowflake } from '../../../src/utils/snowflake';

describe('utils: snowflake', () => {
  
  it('should generate unique IDs', () => {
    const snowflake1 = new Snowflake(0n, 0n);
    const snowflake2 = new Snowflake(0n, 1n);
    const snowflake3 = new Snowflake(1n, 0n);
    const snowflake4 = new Snowflake(1n, 1n);

    const id1 = snowflake1.nextId();
    const id2 = snowflake2.nextId();
    const id3 = snowflake3.nextId();
    const id4 = snowflake4.nextId();

    expect(id1).not.toEqual(id2);
    expect(id1).not.toEqual(id3);
    expect(id1).not.toEqual(id4);
    expect(id2).not.toEqual(id3);
    expect(id2).not.toEqual(id4);
    expect(id3).not.toEqual(id4);
  });

  it('should generate increasing IDs', () => {
    const snowflake = new Snowflake(1n, 2n);
    const id1 = snowflake.nextId();
    const id2 = snowflake.nextId();
    expect(id2).toEqual(id1 + 1n);
  });

  it('should wait until next millisecond if generating IDs too fast', () => {
    const snowflake = new Snowflake(1n, 2n);
    const spy = jest.spyOn(Date, 'now')
      .mockReturnValueOnce(1672531200001) 
      .mockReturnValueOnce(1672531200001);

    const id1 = snowflake.nextId();
    const id2 = snowflake.nextId();
    expect(id2).toEqual(id1 + 1n);

    spy.mockRestore();
  });

  it('should throw error if clock moves backwards', () => {
    const snowflake = new Snowflake(0n, 0n);
    const spy = jest.spyOn(Date, 'now')
      .mockReturnValueOnce(1000000000001)
      .mockReturnValueOnce(1000000000000);

    expect(() => snowflake.nextId()).toThrow();

    spy.mockRestore();
  });

});

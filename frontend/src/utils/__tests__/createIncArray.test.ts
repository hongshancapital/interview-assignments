import { createIncArray } from '..';

describe('createIncArray', () => {
  it('should return empty array when the length is 0', () => {
    const length = 0;

    const incArr = createIncArray(length);

    expect(incArr).toEqual([]);
  });

  it('should create an incremental array', () => {
    const length = 5;

    const incArr = createIncArray(length);

    expect(incArr).toEqual([0, 1, 2, 3, 4]);
  });

  it('should create an array incrementing from the starting number', () => {
    const length = 5;
    const start = 2;

    const incArr = createIncArray(length, start);

    expect(incArr).toEqual([2, 3, 4, 5, 6]);
  });
});

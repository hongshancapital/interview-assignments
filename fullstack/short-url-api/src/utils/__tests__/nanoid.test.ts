import nanoid, { DEFAULT_LENGTH } from '../nanoid';

describe('nanoid tests', () => {
  it('should get a default length random string', () => {
    const id = nanoid();
    expect(id).toHaveLength(DEFAULT_LENGTH);
  });

  it('should get a random string with fixed length', () => {
    const length = 20;
    const id = nanoid(length);
    expect(id).toHaveLength(length);
  });
});

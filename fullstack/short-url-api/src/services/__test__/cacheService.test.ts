import { get, set, clear } from '../cacheService';

function randomKey() {
  return Math.floor(Math.random() * 10e8).toString(32);
}

describe('Cache service', () => {
  beforeEach(() => {
    clear();
  });

  it('Should cache with string', async () => {
    expect(set('key1', 'hello')).toEqual('hello');
  });

  it('Should cache with object', async () => {
    const data = { name: 'Jack', from: 'US' };
    expect(set('key1', data)).toMatchObject(data);
  });

  it('Should get string data', async () => {
    expect(set('key1', 'world')).toEqual('world');
    expect(get('key1')).toEqual('world');
  });

  it('Should get string data with random keys', async () => {
    const key = randomKey();
    const data = 'Hello World!';
    expect(set(key, data)).toEqual(data);
    expect(get(key)).toEqual(data);
  });

  it('Should get object', async () => {
    const data = { name: 'Tom', from: 'China' };
    expect(set('key2', data)).toMatchObject(data);
    expect(get('key2')).toMatchObject(data);
  });
});

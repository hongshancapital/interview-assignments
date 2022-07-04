import { getDbConfig, getRedisConfig } from './';

describe('config test', () => {
  it('get redis env config', async () => {
    process.env.REDIS_HOST = '192.168.0.104';
    process.env.REDIS_PORT = '6378';
    process.env.REDIS_PASSWORD = 'fdsf';
    const {
      config: { host, port, password },
    } = getRedisConfig();
    expect(
      host === '192.168.0.104' && port === '6378' && password === 'fdsf',
    ).toBeTruthy();
  });

  it('get db env config', async () => {
    process.env.POSTGRES_HOST = '192.168.0.104';
    process.env.POSTGRES_PORT = '5435';
    process.env.POSTGRES_USER = 'vizoss1';
    process.env.POSTGRES_PASSWORD = 'vizoss1';
    process.env.POSTGRES_DB = 'fs_hw_vizoss1';
    const { host, port, password, username, database } = getDbConfig();
    expect(
      host === '192.168.0.104' &&
        port === '5435' &&
        password === 'vizoss1' &&
        database === 'fs_hw_vizoss1' &&
        username === 'vizoss1',
    ).toBeTruthy();
  });
});

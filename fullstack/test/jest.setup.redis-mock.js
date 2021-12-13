//@ts-check

jest.mock('redis', () => {
  /**
   * redis-mock已经一年多没有维护，目前不支持promise，不支持connect
   * 方法setex也和redis（setEx）不同，通过mock redis-mock模块来支撑
   *  */

  const redisMock = jest.requireActual('redis-mock')
  const { createClient } = redisMock

  const mockCreateClient = () => {
    const client = createClient()
    const connect = async () => true
    function get(key) {
      return new Promise((reslove) => {
        const value = client._selectedDb.get(key)
        reslove(value ?? null)
      })
    }
    const setEx = (...args) => new Promise((reslove) => client.setex(...args, reslove))
    const flushDb = () => new Promise((reslove) => client.flushdb(reslove))
    Object.assign(client, {
      connect,
      get,
      setEx,
      flushDb,
    })
    return client
  }

  return {
    __esModule: true,
    ...redisMock,
    createClient: mockCreateClient,
  }
})

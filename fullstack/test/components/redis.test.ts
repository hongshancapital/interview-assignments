import redisClient, {
  initializeRedis,
  withCache,
} from '../../src/components/redis'
import timoutAsync from '../../src/utils/timoutAsync'

function cacheString(cacheKey: string) {
  return cacheKey + 'value'
}
describe('withCache', () => {
  test('test initializeRedis', async () => {
    await initializeRedis()
  })
  test('base set and get', async () => {
    const cacheFn = withCache(cacheString, (key) => key)
    const cacheKey = 'cachekey'
    const value = await cacheFn(cacheKey)
    expect(value).toMatch(cacheString(cacheKey))
  })
  test('expire key after 1 second', async () => {
    const cacheFn = withCache(cacheString, (key) => key, 1)
    const expireKey = 'expirekey'
    const value = await cacheFn(expireKey)
    expect(value).toMatch(cacheString(expireKey))
    await timoutAsync(1000)
    const expiredValue = await redisClient.get(expireKey)
    expect(expiredValue).toBeNull()
  })
})

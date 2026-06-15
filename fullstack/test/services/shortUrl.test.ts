import shortUrl, { ShortUrlService } from '../../src/services/shortUrl'
import * as bloomFilter from '../../src/components/bloomFilter'
import mongoClient, { initializeDatabase } from '../../src/components/database'
import env from '../../src/config/env'
jest.mock('../../src/components/bloomFilter')
beforeAll(async () => {
  await initializeDatabase()
})

afterAll(async () => {
  mongoClient.close()
})
describe('shortUrl ', () => {
  test('use hash key generator', async () => {
    const envConfig = { ...env, useHash: true }
    const shortUrlService = new ShortUrlService(envConfig)
    expect(await shortUrlService.create('test.longurl')).not.toBeNull()
  })
  test('when bloomFilter misjudgmentset getKey should return null', async () => {
    const key = 'invalid'
    bloomFilter.add('shorurl:filter', key)
    expect(await shortUrl.getByKey(key)).toBeNull()
  })
})

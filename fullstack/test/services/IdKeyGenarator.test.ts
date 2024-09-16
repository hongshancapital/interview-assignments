import IdKeyGenarator from '../../src/services/IdKeyGenerator'
import mongoClient, {
  collections,
  initializeDatabase,
} from '../../src/components/database'
import env from '../../src/config/env'
describe('IdKeyGenarator', () => {
  beforeAll(async () => {
    await initializeDatabase()
    await collections.Signal.findOneAndUpdate(
      { name: 'shorturl' },
      { $inc: { value: 100000 } },
      { upsert: true, returnDocument: 'after' },
    )
  })

  afterAll(async () => {
    mongoClient.close()
  })

  test('exceed key length should return throw error', async () => {
    const idKeyGenarator = new IdKeyGenarator({ ...env, stepSize: 1000 })
    await expect(idKeyGenarator.generateKey(1)).rejects.toThrow(
      'key length exceed 1',
    )
  })
  test('concurrent  generateKey', async () => {
    const createdConcurrentPromise = async (idx: number, repeat = 20) => {
      const idKeyGenarator = new IdKeyGenarator({ ...env, stepSize: 1 })
      const promise = [...Array(repeat).keys()].map((key) =>
        idKeyGenarator.generateKey(8),
      )
      return await Promise.all(promise)
    }
    await createdConcurrentPromise(2000)
  })
})

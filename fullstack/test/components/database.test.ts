import mongoClient, {
  collections,
  initializeDatabase,
} from '../../src/components/database'
import env from '../../src/config/env'
import { Model, ModelSchma } from '../../src/models/Model'
interface TestModel extends Model {
  name: string
}

jest.mock('../../src/models', () => {
  const modelsMock = jest.requireActual('../../src/models')
  const testModelWithoutIndex: ModelSchma<{ name: string }> = {
    bsonSchema: {
      type: 'object',
      properties: {
        name: {
          type: 'string',
        },
      },
    },
  }
  const testModelWithIndex: ModelSchma<TestModel> = {
    ...testModelWithoutIndex,
    indexes: [[{ name: 1 }, { unique: true }]],
  }

  return {
    __esModule: true,
    default: {
      ...modelsMock.defalut,
      TestModelWithIndex: testModelWithIndex,
      TestModelWithoutIndex: testModelWithoutIndex,
    },
  }
})

describe('database', () => {
  beforeAll(async () => {
    await mongoClient.connect()
    await mongoClient.db(env.mongodbName).createCollection('TestModelWithIndex')
  })
  afterAll(async () => {
    mongoClient.close()
  })
  test('test initializeDatabase', async () => {
    await initializeDatabase()
    expect(collections).toHaveProperty('TestModelWithIndex')
  })
})

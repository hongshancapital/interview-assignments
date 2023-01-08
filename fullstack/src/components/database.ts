import { MongoClient, Collection } from 'mongodb'
import env from '../config/env'
import models from '../models'
import { ModelSchma } from '../models/Model'

type UnwrapGenericParam<T> = T extends ModelSchma<infer A> ? A : never
type ModelTypes = typeof models
type ModelKeys = keyof ModelTypes
type CollectionsType = {
  [m in ModelKeys]: Collection<UnwrapGenericParam<ModelTypes[m]>>
}

const mongoClient: MongoClient = new MongoClient(env.mongodbUrl)
export const collections = {} as CollectionsType

export async function initializeDatabase(): Promise<MongoClient> {
  await mongoClient.connect()
  const db = mongoClient.db(env.mongodbName)
  //todo: 当mongodb集合已存在时，再次调用createCollection会报错，目前采用先查询出集合，判断不存在再进行创建
  const listCollections = await db.listCollections().toArray()
  const collectionNames = listCollections.map((s) => s.name)
  for (const [key, modelSchema] of Object.entries(models)) {
    let collection = db.collection(key)
    if (!collectionNames.includes(key)) {
      collection = await db.createCollection(key)
    }
    const { indexes = [], bsonSchema } = modelSchema
    await db.command({
      collMod: key,
      validationLevel: 'off',
      validator: { $jsonSchema: bsonSchema },
    })

    for (const index of indexes) {
      if (Array.isArray(index)) {
        await collection.createIndex(index[0], index[1])
      } else {
        await collection.createIndex(index)
      }
    }

    Object.assign(collections, { [key]: db.collection(key) })
  }
  return mongoClient
}
export default mongoClient

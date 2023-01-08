import { ObjectId, IndexDirection, CreateIndexesOptions } from 'mongodb'
// https://docs.mongodb.com/manual/reference/operator/query/type/#std-label-document-type-available-types

type ValueType = 'object' | 'array' | 'number' | 'boolean' | 'string'
type BsonType = ValueType | 'double' | 'binData' | 'objectId' | 'date'
export interface BSONSchema<T> {
  bsonType?: BsonType
  type?: ValueType
  description?: string
  required?: Array<keyof T>
  additionalProperties?: boolean | BSONSchema<T>
  properties?: Record<keyof T, BSONSchema<T>>
}
export interface Model {
  _id?: ObjectId
}
type ModelIndex<T> =
  | { [key in keyof T]?: IndexDirection }
  | [{ [key in keyof T]?: IndexDirection }, CreateIndexesOptions]

export interface ModelSchma<T> {
  bsonSchema: BSONSchema<T>
  indexes?: Array<ModelIndex<T>>
}

import { ModelSchma } from './Model'

export interface UrlMapping {
  longUrl: string
  _id: string
}
const urlMappingSchma: ModelSchma<UrlMapping> = {
  indexes: [{ longUrl: 1 }],
  bsonSchema: {
    type: 'object',
    required: ['longUrl'],
    additionalProperties: false,
    properties: {
      _id: { bsonType: 'string' },
      longUrl: {
        bsonType: 'string',
        description: "'longUrl' is required and is a string",
      },
    },
  },
}
export default urlMappingSchma

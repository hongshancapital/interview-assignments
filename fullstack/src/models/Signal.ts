import { Model, ModelSchma } from './Model'
interface Signal extends Model {
  value: number
  name: string
}
const signalSchma: ModelSchma<Signal> = {
  indexes: [[{ name: 1 }, { unique: true }]],
  bsonSchema: {
    type: 'object',
    required: ['value', 'name'],
    additionalProperties: false,
    properties: {
      _id: {},
      value: {
        type: 'number',
        description: "'value' is required and is a number",
      },
      name: {
        type: 'string',
        description: "'name' is required and is a string",
      },
    },
  },
}
export default signalSchma

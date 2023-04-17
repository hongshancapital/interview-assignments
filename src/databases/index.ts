import { connect, connection as db } from 'mongoose'

export default async function connectDatabase (uri: string) {
  db.on('close', () => console.log('Database connection closed.'))
  return connect(uri, { useNewUrlParser: true, useUnifiedTopology: true })
}

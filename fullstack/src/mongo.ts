import mongoose from 'mongoose';
import config from './config/index';

const dbURI = config.DB;
const { Schema } = mongoose;

mongoose.connect(dbURI);

const db = mongoose.connection;

const SlinkSchema = new Schema({
  id: String,
  url: String,
});

mongoose.model('Slink', SlinkSchema);

db.on('connected', () => {
  console.log('database connect succeeded');
});

db.on('error', () => {
  throw new Error(`unable to connect to database at ${dbURI}`);
});

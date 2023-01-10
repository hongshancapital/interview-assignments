import mongoose from 'mongoose';
import { MONGO_URI } from '@base/env';

function createConnection(): void {

  mongoose.connect(`${MONGO_URI}`, {
    useNewUrlParser: true,
    useCreateIndex: true,
    useUnifiedTopology: true,
    autoIndex: false,
  });
}

createConnection();

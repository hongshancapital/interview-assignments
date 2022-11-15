import { Schema, model, connect } from 'mongoose';
import config from './config/index';
import { ReqInfo } from './constant';

const dbURI = config.DB;

const schema = new Schema<ReqInfo>({
  id: { type: String, required: true },
  url: { type: String, required: true },
});

model<ReqInfo>('Slink', schema);

async function run(): Promise<void> {
  await connect(dbURI);
};

run();

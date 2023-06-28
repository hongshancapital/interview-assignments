import { createConnection, Model } from "mongoose";
import { config } from "../../config";
import { TinyURLSchema } from "./persist.model";

export class PersistService {
  constructor(readonly model: Model<TinyURLSchema>, readonly ttl: number) {}

  async set(alias: string, url: string) {
    const model = new this.model({
      url,
      alias,
      expireAt: Date.now() + this.ttl,
    });

    await model.save();
  }

  async get(alias: string) {
    const doc = await this.model.findOne({ alias });

    if (!doc) return null;

    // 过期处理
    if (doc.expireAt < Date.now()) {
      await doc.remove();
      return null;
    }

    return doc.toJSON();
  }
}

let instance: PersistService | undefined;

export function getPersistService() {
  if (!instance) {
    const connection = createConnection(config.PERSIST_MONGODB_URI);
    const model = connection.model("tiny_url", TinyURLSchema);
    instance = new PersistService(model, config.TTL);
  }

  return instance;
}

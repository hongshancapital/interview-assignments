import { Db, MongoClient } from "mongodb";
import { IDomainStoreEntity } from "./entities";

const DATABASE_NAME = "short_domain";
const COLLECTION_NAME = "urls";
const INDEX_NAME = "short_url_index";

export interface IDomainStoreRepo {
  getLongDomain(short: string): Promise<string | null>;
  registerShortDomain(short: string, long: string): Promise<void>;
}

export class DomainStoreRepo implements IDomainStoreRepo {
  constructor(private db: Db) {}

  static async init(uri: string): Promise<DomainStoreRepo> {
    const cli = new MongoClient(uri);
    await cli.connect();
    const collection = cli.db(DATABASE_NAME).collection<IDomainStoreEntity>(COLLECTION_NAME);
    if (!await collection.indexExists(INDEX_NAME)) {
      await collection.createIndex("short", {name: INDEX_NAME})
    }
    return new DomainStoreRepo(cli.db(DATABASE_NAME));
  }

  async getLongDomain(short: string): Promise<string | null> {
    const entity = await this.db.collection<IDomainStoreEntity>(COLLECTION_NAME).findOne({ short });
    if (entity) {
      return entity.long;
    } else {
      return null;
    }
  }

  async registerShortDomain(short: string, long: string): Promise<void> {
    await this.db.collection<IDomainStoreEntity>(COLLECTION_NAME).insertOne({ short, long });
  }
}


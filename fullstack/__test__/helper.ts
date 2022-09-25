import { connections } from "mongoose";
import { getCacheService } from "../src/modules/cache/cache.service";
import { getIDService } from "../src/modules/id/id.service";
import { getPersistService } from "../src/modules/persist/persist.service";

export async function reset() {
  await Promise.all([
    getIDService().model.deleteMany(),
    getPersistService().model.deleteMany(),
    getCacheService().model.flushall(),
  ]);
}

export async function close() {
  await getCacheService().model.disconnect();

  for (const conn of connections) {
    await conn.close();
  }
}

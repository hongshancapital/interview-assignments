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
  const promises: Promise<unknown>[] = [getCacheService().model.quit()];

  for (const conn of connections) {
    promises.push(conn.close());
  }

  await Promise.all(promises);
}

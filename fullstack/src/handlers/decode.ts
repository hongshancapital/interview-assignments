import { Handler } from "express";
import { getCacheService } from "../modules/cache/cache.service";
import { getPersistService } from "../modules/persist/persist.service";
import { isAlias } from "../utils/validate";

export const decodeHandler: Handler = async (req, res) => {
  const alias = req.body?.alias;
  if (!isAlias(alias)) {
    res.status(400).json({ message: "Parameter error" });
    return;
  }

  let url = await getCacheService().get(alias);

  if (!url) {
    const doc = await getPersistService().get(alias);
    if (doc) {
      url = doc.url;
      await getCacheService().set(
        doc.url,
        doc.alias,
        doc.expireAt - Date.now()
      );
    }
  }

  if (!url) {
    res.status(404).json({ message: "Not found" });
    return;
  }

  res.json({ url });
};

import { Handler } from "express";
import { getCacheService } from "../modules/cache/cache.service";
import { getIDService } from "../modules/id/id.service";
import { getPersistService } from "../modules/persist/persist.service";
import { isURL } from "../utils/validate";

export const encodeHandler: Handler = async (req, res) => {
  const url = req.body?.url;

  if (!isURL(url)) {
    res.status(400).json({ message: "Parameter error" });
    return;
  }

  const alias = await getIDService().generate();

  await Promise.all([
    getPersistService().set(alias, url),
    getCacheService().set(alias, url),
  ]);

  res.json({ alias });
};

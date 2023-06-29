import express from "express";
import { assets as browserAssets } from "./browser/index.js";

export function assets() {
  const router = express.Router();

  router.use(...browserAssets());

  return router;
}

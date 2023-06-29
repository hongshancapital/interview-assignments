import express from "express";

import { assets } from "./assets/index.js";
import { api } from "./api/index.js";

export function middlewares() {
  const router = express.Router();

  router.use(express.json());

  router.use("/api", api());

  router.use(assets());

  return router;
}

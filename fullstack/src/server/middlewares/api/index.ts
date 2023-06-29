import express from "express";

import { createShortLink } from "./path/createShortLink.js";
import { getOriginLinkById } from "./path/getOriginLinkById.js";

export function api() {
  const router = express.Router();

  // healthy check
  router.get("/", (req, res) => res.sendStatus(204));

  router.post("/createShortLink", createShortLink);
  router.get("/go/:shortLinkId", getOriginLinkById);

  router.all("*", (req, res) => res.sendStatus(404));

  return [router];
}

import type { RequestHandler } from "express";
import { assets as browserAssets } from "./dist-browser.js";

export function assets(): RequestHandler[] {
  return browserAssets();
}

import { RequestHandler } from "express";

export interface Controller {
  path: string;
  get: RequestHandler;
  post: RequestHandler;
}
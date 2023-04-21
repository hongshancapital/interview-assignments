import { Router } from "express";
import { createShortUrl, getLongUrl } from "./controllers/shortUrlController";

const routes = Router();

routes.post("/shorturl", createShortUrl);
routes.get("/shorturl/:code", getLongUrl);

export default routes;

import {Router} from "express";
import { getOriginalUrl, createShortLink } from "../controllers/ShortLinkController";

const routers = Router();

routers.post("/api/v1/shortLinks/shortUrl", createShortLink);
routers.get("/api/v1/shortLinks/originalUrl", getOriginalUrl);

export default routers;

import { Router } from "express";
import { body, param } from 'express-validator';
import { transferDomainToUrl, transferUrlToDomain } from "./api/domainApi"

const router = Router();

router.get("/", (req, res) => { res.send("ok") });
router.get("/api/domain/url_to_domain/:url", param("url").isLength({ max: 8 }).trim(), transferUrlToDomain);
router.post("/api/domain/domain_to_url", body("domain").isLength({ max: 100 }), transferDomainToUrl);

// router.all("*", (req, res) => {
//     res.status(200).sendFile("/", { root: __dirname + "ui_dist" });
// });
export { router };
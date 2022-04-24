import express from "express";
import { ShortUrlBiz } from "./biz/impl";
import { DomainStoreRepo } from "./repo/domain-store";
import { body, param, validationResult } from 'express-validator';

async function boostrap() {
  const app = express();
  const repo = await DomainStoreRepo.init("mongodb://localhost:27017/");
  const biz = new ShortUrlBiz(repo);

  app.post(
    "/register", 
    body("url").isURL(),
    async (req, res) => {
      const validationErrors = validationResult(req);
      if (validationErrors.isEmpty()) {
        const shortUrl = await biz.register(req.body.url);
        if (shortUrl === null) {
          res.send({ success: false, message: "" });
        } else if (typeof shortUrl === "string") {
          res.send({ success: true, data: shortUrl });
        } else {
          res.status(500).send({ success: false, message: shortUrl.err });
        }
        return;
      } else {
        res.status(400).send({ success: false, message: "validation error", detail: validationErrors });
      }
      res.end();
    }
  )

  app.get(":url", param("url"), async (req, res) => {
    const validationErrors = validationResult(req);
    if (validationErrors.isEmpty()) {
      const target = await biz.visit(req.params?.url)
      if (target === null) {
        res.status(404).send({ success: false, message: "url not registered" });
      } else if (typeof target === "string") {
        res.redirect(target);
      } else {
        res.status(500).send({ success: false, message: target.err })
      }
      // res.redirect(url)
    } else {
      res.status(400).send({ success: false, message: "validation error", detail: validationErrors });
    }
    res.end()
  })

  app.listen(3000);
}

boostrap()


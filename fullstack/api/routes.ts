import { Router, Request, Response } from "express";
import urlShortener from "./shorten";
import urlUnshortener from "./unshorten";

const router = Router();

router.post("/shorten", (req: Request, res: Response) => {
  urlShortener(req.body.url)
    .then((result) => {
      res.status(200).send(result);
    })
    .catch((err) => {
      res.status(500).send(err);
    });
});

router.post("/unshorten", (req: Request, res: Response) => {
  urlUnshortener(req.body.url)
    .then((result) => {
      res.status(200).send(result);
    })
    .catch((err) => {
      res.status(500).send(err);
    });
});

export default router;

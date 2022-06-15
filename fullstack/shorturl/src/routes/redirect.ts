import { Router, Request, Response, NextFunction } from 'express';

import RestResponse from "../infra/RestResponse";

const router = Router();

router.get("/:alias", function (req: Request, res: Response, next: NextFunction) {
  let url = 'https://www.baidu.com';

  if (!url) {
    res.status(404).send(RestResponse.failure("Not Found."));
  }

  res.redirect(url, 301);
});

export default router;

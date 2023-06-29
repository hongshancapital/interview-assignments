import type { RequestHandler } from "express";
import * as shortlinkService from "../services/shortlinkService.js";

type CreateShortLinkBody = {
  originLink: string;
};

export const createShortLink: RequestHandler<
  unknown,
  unknown,
  CreateShortLinkBody
> = (req, res) => {
  const { originLink } = req.body;

  shortlinkService.createShortLink(originLink).then(
    (value) => {
      const shortlinkURL = `/api/go/${value}`;
      res.status(200).send(shortlinkURL);
    },
    (err: any) => res.status(400).send(err)
  );
};

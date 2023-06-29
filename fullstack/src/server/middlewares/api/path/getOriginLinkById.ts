import type { RequestHandler } from "express";
import * as shortlinkService from "../services/shortlinkService.js";

type GetOriginLinkByIdParams = {
  shortLinkId: string;
};

export const getOriginLinkById: RequestHandler<GetOriginLinkByIdParams> = (
  req,
  res
) => {
  const { shortLinkId } = req.params;

  shortlinkService.getOriginLinkById(shortLinkId).then(
    (value: string) => {
      res.redirect(302, value);
    },
    (err: any) => res.status(404).send("Link not found.")
  );
};

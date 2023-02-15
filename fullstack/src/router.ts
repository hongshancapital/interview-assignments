import { Request, Response, isLink, isID, isEmpty, successBody, errorBody } from './utils';

import { getService } from './service/factory';
import { IShortLinkService } from './service/base';

interface SaveLinkReqBody {
  link: string;
}

type SaveLinkResData = string;

interface GetLinkQuery {
  link?: string;
  id?: string;
}

type GetLinkResData = string;

type GetLinkReqBody = null;

export class Router {
  service: IShortLinkService;
  constructor(service: IShortLinkService = getService()) {
    this.service = service;
  }
  async saveLink(req: Request<SaveLinkResData, SaveLinkReqBody>, res: Response<SaveLinkResData>) {
    const valid = isLink(req.body?.link);

    if (valid) {
      return res.send(successBody(await this.service.saveLink(req.body.link)));
    } else {
      return res.send(errorBody('param_link_invalid', 'Link 为空或格式不正确'));
    }
  }
  async getLink(
    req: Request<GetLinkResData, GetLinkReqBody, GetLinkQuery>,
    res: Response<GetLinkResData>,
  ) {
    if (isEmpty(req.query.id) && isEmpty(req.query.link)) {
      return res.send(errorBody('param_invalid', 'ID 和 Link 至少传一个'));
    }
    let id: string = '';

    if (req.query.id) {
      ({ id } = req.query);
    } else if (req.query.link) {
      const { error, id: suId } = this.service.parseShortLink(req.query.link);

      if (error || !suId) {
        return res.send(errorBody('param_link_invalid', 'Link 格式不正确'));
      }
      id = suId;
    }

    if (!isID(id)) {
      return res.send(errorBody('param_id_invalid', 'ID 格式不正确'));
    }
    return res.send(successBody(await this.service.getLink(id)));
  }
}

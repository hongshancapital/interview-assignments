import { Request, Response } from 'express';
import validUrl from 'valid-url';
import { BaseRes, LongURLRes } from '../types/type'
import { URLSvc } from '../service/url'
import { ResSuccess, ResError } from '../utils/res'

class URLController {
  private svc: URLSvc
  constructor(svc: URLSvc) {
    this.svc = svc;
    this.getLongURL = this.getLongURL.bind(this)
    this.generateShortURL = this.generateShortURL.bind(this)
  }

  async getLongURL (req: Request, res: Response<BaseRes<LongURLRes>>) {
    const shortURL = req.params.shortURL
    const isValid = /^[a-zA-Z0-9]{1,8}$/.test(shortURL)
    if (!isValid) {
      res.status(400).json(ResError('unvalid shortURL'))
      return
    }
    try {
      const longURL = await this.svc.getURL(shortURL)
      if (!longURL) {
        res.status(200).json(ResError('not found', 10404))
        return
      }
      res.status(200).json(ResSuccess({
        longURL,
      }))
    } catch (error) {
      console.log('controller.getLongURL err:', error)
      res.status(500).json(ResError('internal error'))
    }
    
  }

  async generateShortURL(req: Request, res: Response) {
    const url = req.body.url
    if (!validUrl.isUri(url)) {
      res.status(400).json(ResError('unvalid url'))
      return
    }

    try {
      const shortURL = await this.svc.saveURL(url)
      res.status(200).json(ResSuccess({
        shortURL,
      }))
    } catch (error) {
      console.log('controller.getLongURL err:', error)
      res.status(500).json(ResError('internal error'))
    }
  }
}


export { URLController }
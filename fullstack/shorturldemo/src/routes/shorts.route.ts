import { Router } from 'express';
import ShortsController from '@controllers/shorts.controller';
import { Routes } from '@interfaces/routes.interface';
import validationMiddleware from '@middlewares/validation.middleware';
import { CreateShortDto } from '@/dtos/shorts.dto';

class ShortsRoute implements Routes {
  public path = '/shorts';
  public router = Router();
  public shortsController = new ShortsController();

  constructor() {
    this.initializeRoutes();
  }

  private initializeRoutes() {
    /**,
    * @swagger
    * /shorts/{shortKey}:
    *    get:
    *      tags:
    *      - 短域名接口
    *      summary: 读取短域名信息
    *      produces:
    *        - application/json
    *      parameters:
    *        - name: shortKey
    *          in: path
    *          description: 短域名Key
    *          required: true
    *          type: string
    *      responses:
    *        200:
    *          description: 'OK'
    *        400:
    *          description: 'Bad Request'
    *        409:
    *          description: 'Conflict'
    *        500:
    *          description: 'Server Error'
    * */
    this.router.get(`${this.path}/:shortKey`, this.shortsController.getUserByShortKey);

    /**,
    * @swagger
    * /shorts:
    *    post:
    *      tags:
    *      - 短域名存储
    *      summary: 生成并存储短域名
    *      produces:
    *        - application/json
    *      parameters:
    *        - name: origin
    *          in: body
    *          description: url
    *          required: true
    *          type: string
    *      responses:
    *        201:
    *          description: 'Created'
    *        400:
    *          description: 'Bad Request'
    *        409:
    *          description: 'Conflict'
    *        500:
    *          description: 'Server Error'
    * */
    this.router.post(`${this.path}`, validationMiddleware(CreateShortDto, 'body'), this.shortsController.createShort);

  }
}

export default ShortsRoute;

import { NextFunction, Request, Response } from 'express';
import { CreateUserDto } from '@dtos/users.dto';
import ShortService from '@services/shorts.service';
import md5 from 'md5'
import { Short } from '@/interfaces/shorts.interface';
import { CreateShortDto } from '@/dtos/shorts.dto';
import { ShortModel } from '@/models/shorts.model';

class UsersController {
  public shortService = new ShortService();

  public getUserByShortKey = async (req: Request, res: Response, next: NextFunction) => {
    try {
      // console.log('req.params', req.params)
      const shortKey = req.params.shortKey;
      const findOneShortData: Short = await this.shortService.findShortByShortKey(shortKey as string);

      const jShort = (findOneShortData as ShortModel).get?.({plain: true}) || findOneShortData;
      // 移除敏感信息
      delete jShort['id']
      delete jShort['md5']
      delete jShort['createdAt']
      delete jShort['updatedAt']

      res.status(200).json({ data: findOneShortData, message: 'findOne' });
      
      // 重定向可以走下面的方式，301永久重定向。当然路由path路劲就需要改成根目录了
      // res.status(301).redirect(`https://baidu.com/${jShort.shortKey}`)
    } catch (error) {
      next(error);
    }
  };

  public createShort = async (req: Request, res: Response, next: NextFunction) => {
    try {
      const shortData: CreateShortDto = req.body;
      const createShortData: Short = await this.shortService.createShort(shortData);

      res.status(201).json({ data: createShortData, message: 'created' });
    } catch (error) {
      next(error);
    }
  };

}

export default UsersController;

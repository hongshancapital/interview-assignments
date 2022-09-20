import { Request, Response } from 'express';
import logger from '../utils/logger';

export default function (req: Request, res: Response): void {
  logger.error(`404请求: ${req.path}`)
  res.status(404).send('request not found')
}
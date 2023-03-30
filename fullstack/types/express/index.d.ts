declare namespace Express {
  export interface Request {
    redisClient?: any;
    db: any;
  }
}

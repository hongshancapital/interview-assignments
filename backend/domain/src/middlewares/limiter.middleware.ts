import { Request, Response, NextFunction } from 'express';

export class TokenBucket {
  private capacity: number;
  private tokens: number;
  private fillRate: number;
  private lastFillTime: number;

  constructor(capacity: number, fillRate: number) {
    this.capacity = capacity;
    this.tokens = capacity;
    this.fillRate = fillRate;
    this.lastFillTime = Date.now();
  }

  public getToken(apply: number): boolean {
    const now = Date.now();
    const elapsedTime = now - this.lastFillTime;
    this.tokens = Math.min(this.capacity, this.tokens + this.fillRate * ( elapsedTime / 1000));
    
    if (this.tokens < apply) {
      return false;
    } else {
      this.tokens--;
      this.lastFillTime = now;
      return true;
    }
  }
}

const tokenBucket = new TokenBucket(200, 100);

export const rate = (req: Request, res: Response, next: NextFunction) => {
  if (tokenBucket.getToken(1)) {
    next();
  } else {
    res.status(429).json({ status: 429, message: 'Too Many Requests'});
  }
};
import { NextFunction, Response, Request } from "express";
import config from '../config'; 
import { ApplicationError } from "../helpers/application.err";
import { verifyToken } from '../utils/jwt';
import { User } from "../interfaces/user.interface";

const getAuthorization = (req: Request) => {
  if (req.cookies) {
    const cookie = req.cookies["Authorization"] as string;
    if (cookie) return cookie;
  }

  const header = req.header("Authorization");
  if (header) {
    return header.split("Bearer ")[1];
  }

  return null;
};

export const auth = async (req: Request, res: Response, next: NextFunction) => {
  try {
    const authorization = getAuthorization(req);
    if (authorization) {
      const user = (await verifyToken(authorization, config.get('secretKey')))as User;
      if (user) {
        (req as any).user = user;
        next();
      } else {
        next(new ApplicationError(401, "Wrong authentication token"));
      }
    } else {
      next(new ApplicationError(404, "Authentication token missing"));
    }
  } catch (error) {
    next(new ApplicationError(401, "Wrong authentication token"));
  }
};

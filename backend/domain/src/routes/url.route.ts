import { Router } from "express";
import { auth } from "../middlewares/auth.middleware";
import { isBlack } from "../middlewares/black.middleware";
import { UrlController }from "../controllers/url.controller";
import { container, singleton } from 'tsyringe';

@singleton()
export class UrlRoutes {
  public path = "/";
  private router: Router;
 
  constructor(public urlController: UrlController) {
    this.router = Router();
    this.initializeRoutes();
  }

  private initializeRoutes() {
    this.router.post(
      `${this.path}url`,
      auth, isBlack,
      this.urlController.create.bind(this.urlController)
    );
    this.router.get(
      `${this.path}:code`, isBlack,
      this.urlController.redirect.bind(this.urlController)
    );
  }

  public getRouter() {
    return this.router;
  }
}

const urlRoutes = container.resolve(UrlRoutes);
export default urlRoutes;
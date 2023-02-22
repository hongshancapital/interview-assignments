
import express, { Express, Request, Response } from "express";

import { URLStore } from "./store/URLStore";
import { URLService } from "./service/URLService";

class App {
  app: Express;
  constructor() {
    this.app = express();
    this.start = this.start.bind(this);
  }

  start() {
    const { app } = this;
    app.use(express.json());
    app.use(express.text());
    app.use(express.urlencoded({ extended: true }));

    const urlStore = new URLStore();
    const urlService = new URLService(urlStore);

    app.post("/long2short", urlService.getShortURL);
    app.get("/short2long/:url", urlService.getLongURL);
    return app;
  }
}

export { App };

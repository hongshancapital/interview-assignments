import express, { Express, Request, Response } from "express";
import dotenv from "dotenv";
import { stringify } from "querystring";
import { PrismaClient } from "@prisma/client";
import { insertUrlAndGetId, findUrlById } from "./model";

dotenv.config();

const app: Express = express();
const port = process.env.PORT;

app.get("/shorter", (req: Request, res: Response) => {
  if (null === req.query.url)
  {
    res.send(`?url=https://url.com/to/ is not provide`);
    return false;
  }

  let path = `${req.query.url}`;

  insertUrlAndGetId(path).then(
    (url_map: { id: number; url: string }|null) => {
      if (null !== url_map){
        res.send(`${req.query.url} \n will be redirect to:  \n <a href="${url_map.id}">${url_map.id}</a>`);        
        return false;
      }
      else {
        res.send(`${req.query.url} \n get shorter url error!`);                
      }
      return false;
    },
    ()=> {
      res.send(`${req.query.url} \n get shorter url error!`);
    });

});

app.get("/testurl/:id", async (req: Request, res: Response) => {
  let id: string = req.params.id;
  res.send(`we get ${id}`);
});

app.get("/", (req: Request, res: Response) => {
  let id: string = req.params.id;
  res.send(`
  <h4>your need first loading db.sql to mysql with mysql://root:plkj876ft5@localhost:3306/shorter,
  then run npx run dev</h4>
  <div>visit: <a href='http://localhost:8000/shorter?url=https://hahahaha.com1'
  >http://localhost:8000/shorter?url=https://hahahaha.com1 for a shorter Url</a></div>
  <p>visit http://localhost:8000/testurl/1010 you get 1010</p>
  <p>visit http://localhost:8000/:id to call for redirect, eg: http://url.to/1010 </p>
  `);
});

app.get("/:id", async (req: Request, res: Response) => {
  if ((null === req.params.id) || (req.params.id !== `${parseInt(req.params.id)}`))
  {
    res.send(`we get invalid shorter url: /${req.params.id}`); 
    return false;
  }

  if (req.params.id.length <= 8) {
    findUrlById(req.params.id).then(
      (url: { url: string; id: number } | null) => {
        if (!(null === url))
         res.redirect(`${url.url}`);
         else
         res.send(`url: /${req.params.id} not found`); 
      },
      ()=>{        
        res.redirect(`/`);}
    );
  } else res.redirect(`/`);
});

export function server(): void {
  app.listen(port, () => {
    console.log(`⚡️[server]: Server is running at http://localhost:${port}`);
  });
}

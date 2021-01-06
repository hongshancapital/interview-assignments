
import { generateToolboxNames } from '../models';
import express from 'express';
import db from '../db';

const toolboxRouter = express.Router();

toolboxRouter.get(
  '/toolbox',
  (req, res) => {
    const sql = "select * from toolbox_prefs order by id desc";
    console.log('get /toolbox/');
    db.all(sql, [], (err, rows) => {
      if (err) {
        res.status(400).json({ "error": err.message });
        return;
      }
      res.json({
        "message": "success",
        "data": rows
      })
    });
  }
);
toolboxRouter.get(
  '/toolbox/options',
  (req, res) => {
    console.log('get /toolbox/options');
    res.json(generateToolboxNames());
  }
);

toolboxRouter.post(
  '/toolbox/create',
  (req, res) => {
    console.dir(req.body);
    var errors = []
    if (!req.body.name) {
      errors.push("No name specified");
    }
    if (!req.body.tools) {
      errors.push("No tools specified");
    }
    if (errors.length) {
      res.status(400).json({ "error": errors.join(",") });
      return;
    }
    const sql = 'INSERT INTO toolbox_prefs (name, tools) VALUES (?,?)';

    db.run(sql, [req.body.name, req.body.tools], (err: Error) => {
      if (err) {
        res.status(400).json({ "error": err.message })
        return;
      }
      res.json({
        "message": "success",
      })
    });
  }
);

export default toolboxRouter;
import express from "express";
import bodyParser from "body-parser";
import { nanoid } from "nanoid";
import { model } from "./db";
const app = express();
app.use(bodyParser.json());

app.get('/:url', async (req, res) => {
  const _id = req.params.url;
  const data = await model.findById(_id);
  if (!data) {
    return res.send({ message: 'url 404', data: null });
  }
  res.send({ message: 'success', data });
});
app.post('/url', async (req, res) => {
  const body = req.body;
  const longUrl = body.longUrl;
  if (!longUrl) {
    return res.send({ message: 'invalid url', data: null });
  }
  const data = await model.findOne({ longUrl });
  if (data) {
    return res.send({ message: 'success', data: { _id: data._id } });
  }
  const _id = nanoid(8);
  await model.create({ _id, longUrl });
  res.send({ message: 'success', data: { _id } });
})
const server = app.listen(3000);
export const httpServer = server;
import * as express from "express";
import connect from "./utils/db";
import { Gid } from "./utils/Gid";

const connectUrl = process.env.CONNECT_URL;
const machineId = Number(process.env.MACHINE_ID);
const instanceId = Number(process.env.INSTANCE_ID);
if (!connectUrl) {
  throw Error("ConnectUrl must be string");
}
if (!(machineId === 0 || machineId === 1)) {
  throw Error("MachineId must be 0 or 1");
}
if (
  !(
    instanceId === 0 ||
    instanceId === 1 ||
    instanceId === 2 ||
    instanceId === 3
  )
) {
  throw Error("InstanceId must >= 0 and <= 3");
}

// establish database connection
const connection = connect(connectUrl);

const gidGenerator = new Gid({
  machineId,
  instanceId,
});

const app = express();
app.use(express.json());

// get long link
app.get("/:short_url", async function (req, res) {
  const links = await connection.then((db) =>
    db.collection("links").findOne({
      short_url: req.params.short_url,
    })
  );
  links?.url
    ? res.status(200).send(links?.url)
    : res.status(501).send("Invalid short url");
});

// create short link
app.post("/shortlink", async function (req, res) {
  const { url } = req.body;
  if (url.match(/^http[s]?:\/\/[^/]+/g)) {
    const short_url = gidGenerator.bitToBase64(gidGenerator.generate());
    const { insertedId } = await connection.then((db) =>
      db.collection("links").insertOne({
        url,
        short_url,
        create_time: Date.now(),
      })
    );
    return insertedId
      ? res.status(200).send(`/${short_url}`)
      : res.status(501).send("Generate failed");
  } else {
    res.status(501).send("Invalid url");
  }
});

// handle 404
app.use((_req, res) => {
  res.status(404).send("404 Not Found");
});

app.listen(3000);
console.log("listening on port 3000");

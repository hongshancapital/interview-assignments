import * as express from "express";
import * as mongoose from "mongoose";
import router from "./controller/shortUrl.controller";

const app = express();
const bodyParser = require("body-parser");
app.use(bodyParser.json());
export default app;

const MongoDBUri: string = "mongodb://127.0.0.1:27017/mydb";

mongoose.set("strictQuery", true);
mongoose
  .connect(MongoDBUri)
  .then(() => {
    console.log("MongoDB Connected!");

    app.use("/", router);
    app.listen(9000, () => {
      console.log("Server is listening on port 9000");
    });
  })
  .catch((err) => {
    console.error("Connect MongoDB Failed!");
    process.exit();
  });

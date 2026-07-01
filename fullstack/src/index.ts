import dotenv from "dotenv";
import app from "./app";




dotenv.config();


app.listen(app.get("port"), () => {
    console.log(
      "App is running on http://localhost:%d in %s mode",
      app.get("port"),
      app.get("env")
    );
  });

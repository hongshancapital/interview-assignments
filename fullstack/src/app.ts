import express from "express";
import dotenv from "dotenv";
import * as api from "./routes/api";// Our Express APP config
dotenv.config();
// console.log(process.env);
const app = express();
app.set("port", process.env.PORT || 8080);

// API Endpoints
app.get("/", (req, res) => {
  res.send("api");
});
app.use( express.json() );
// Configure routes
api.register( app );


// export our app
export default app;
import express from "express";
import path from "path";

// Controllers (route handlers)
import * as apiController from "./controllers/api";

// Create Express server
const app = express();
const port = 80
// Express configuration
app.set("port", port);
app.use((req, res, next) => {
    next();
});
app.use(express.json());
app.use(express.text());
app.use(express.urlencoded({ extended: false }));
app.use(
    express.static(path.join(__dirname, "public"), { maxAge: 31557600000 })
);

/**
 * API routes.
 */
app.get("/api", apiController.getApi);
app.post("/api/long2short", apiController.long2short);
app.post("/api/short2long", apiController.short2long);
export default app;

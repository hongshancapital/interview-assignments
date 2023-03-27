import mysqlFuncs from "./database/mysql";
import app from "./app";

mysqlFuncs
  .dbConnect()
  .then(() => mysqlFuncs.dbInit())
  .then(() => {
    const port = process.env.PORT || 8080;

    app.listen(port, () => {
      console.log(`[server]: Server is running at http://localhost:${port}`);
    });
  })
  .catch((err) => console.error(err));

/*
 * @Author: zhangyan
 * @Date: 2023-03-10 17:06:59
 * @LastEditTime: 2023-03-12 17:25:57
 * @LastEditors: zhangyan
 * @FilePath: /fullstack/server.ts
 * @Description: express启动监听，为了避免端口冲突，单独提出来
 */
import app from "./app";

const PORT = 3000;

app.listen(PORT, () => {
  console.log(`Express with Typescript! http://localhost:${PORT}`);
});

app.on("error", (error: any) => {
  if (error.syscall !== "listen") {
    throw error;
  }
  var bind = typeof PORT === "string" ? "Pipe " + PORT : "Port " + PORT;

  switch (error.code) {
    case "EACCES":
      console.error(bind + " requires elevated privileges");
      process.exit(1);
    case "EADDRINUSE":
      console.error(bind + " is already in use");
      process.exit(1);
    default:
      throw error;
  }
});

import { Server } from "./server"
import cfg from "./config/config"

Server.InitServer().then(()=>{
    //create http server
    var app = Server.bootstrap().app;
    app.set("port", cfg.appServerPort);

    var http = require("http");
    var httpServer = http.createServer(app);

    //listen on provided ports
    httpServer.listen(cfg.appServerPort);

    //add error handler
    httpServer.on("error", onError);

    //start listening on port
    httpServer.on("listening", onListening);

}).catch(val => {
    console.log("start Server FAIL!");
    console.log(val);
})


/**
 * Event listener for HTTP server "error" event.
 */
function onError(error: { syscall: string; code: any; }) {
  if (error.syscall !== "listen") {
    throw error;
  }
  // handle specific listen errors with friendly messages
  switch (error.code) {
    case "EACCES":
      console.error(cfg.appServerPort.toString() + " requires elevated privileges");
      process.exit(1);
      break;
    case "EADDRINUSE":
      console.error(cfg.appServerPort.toString() + " is already in use");
      process.exit(1);
      break;
    default:
      throw error;
  }
}

/**
 * Event listener for HTTP server "listening" event.
 */
function onListening() {
  console.log("Listening on " + cfg.appServerPort.toString());
}

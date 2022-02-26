import http from 'http'
import { Server } from "./server"
import cfgs from "./config/config"

Server.InitServer().then(()=>{
    //create http server
    var app = Server.bootstrap().app;
    app.set("port", cfgs.server_port);
    var httpServer = http.createServer(app);

    //listen on provided ports
    httpServer.listen(cfgs.server_port);

    //add error handler
    httpServer.on("error", onError);

    //start listening on port
    httpServer.on("listening", onListening);

}).catch(val => {
    console.log("start Server FAIL!");
    console.log(val);
})



/**
 * Normalize a port into a number, string, or false.
 */
function normalizePort(val: string) {
  var port = parseInt(val, 10);

  if (isNaN(port)) {
    // named pipe
    return val;
  }

  if (port >= 0) {
    // port number
    return port;
  }

  return false;
}

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
      console.error(cfgs.server_port.toString() + " requires elevated privileges");
      process.exit(1);
      break;
    case "EADDRINUSE":
      console.error(cfgs.server_port.toString() + " is already in use");
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
  console.log("Listening on " + cfgs.server_port.toString());
}

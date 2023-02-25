import server from "./server";
import controller from './controller';
import ErrorHandler from './errors/middleware';

function bootstrap()  {
  const port = 8080;
  controller.Bind(server);
  server.use(ErrorHandler);
  server.listen(port, () => {
    console.log("server listen at ",  port);
  });
};

bootstrap();
import { App } from "./app";
import * as utils from "./utils/utils";

new App().start().listen(utils.PORT, () => {
  console.log("[server]: Server is running at http://localhost:" + utils.PORT);
});


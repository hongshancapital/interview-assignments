import { app } from "./app";
import { config } from "./config";

app.listen(config.PORT, () => {
  console.log(`server(${config.NODE_ID}) start at :${config.PORT}`);
});

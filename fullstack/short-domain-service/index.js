const port = 9001;
const app = require("./src/main");

app.listen(port, () => {
  console.log(`App listening on port ${port}`);
});
import app from "./app";
import { createTable } from "./db";

const PORT = process.env.PORT || 3003;

app.listen(PORT, async () => {
  await createTable();
  console.log(`⚡️[server]: Server is running at http://localhost:${PORT}`);
});

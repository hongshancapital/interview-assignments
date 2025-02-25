require('dotenv').config();

import app from './app';
import { connectDb } from './schemas';

const port = process.env.PORT || 3000;

(async () => {
  try {
    await connectDb();
    app.listen(port, () => {
      console.log(`Express server started on port: ${port}`);
    });
  } catch (err) {
    console.error(err);
    process.exit();
  }
})();

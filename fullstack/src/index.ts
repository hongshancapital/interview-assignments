import express from 'express';
import { connectDB } from './config/db'
import Index from './routes/index'
import Url from './routes/url'

const app = express();

connectDB();

app.use('/', Index);
app.use('/api/url', Url);

const port = 5000;

app.listen(port, () => {
  console.log(`Server running on port ${port}`);
});
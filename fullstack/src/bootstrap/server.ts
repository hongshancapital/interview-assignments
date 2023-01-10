import app from './app';
import { PORT } from '@base/env';

app.listen(PORT, () => console.log('Server is running'));


import express from 'express';
import bodyParser from 'body-parser';
import router from './router';

const port = 3005;
const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }))
app.use(router);

app.listen(port, () => {
	console.log(`Server is running on http://127.0.0.1:${port}/`);
})

export { app }
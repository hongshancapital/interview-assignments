import config from 'config';
import app from './app';

const PORT = process.env.HTTP_PORT || config.get('port');

app.listen(PORT, function () {
    console.log('Server success run at the port', PORT);
});

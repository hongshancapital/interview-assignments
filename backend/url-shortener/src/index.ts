import { app, loadResource } from './app';
import config from 'config';

const PORT = config.get<number>('port');

loadResource().then(() => {
    app.listen(PORT, () => {
        console.log(`Express with Typescript! http://localhost:${PORT}`);
    });
});

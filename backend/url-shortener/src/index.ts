import { app, loadResource } from './app';

const PORT = 3000;

loadResource().then(() => {
    app.listen(PORT, () => {
        console.log(`Express with Typescript! http://localhost:${PORT}`);
    });
});

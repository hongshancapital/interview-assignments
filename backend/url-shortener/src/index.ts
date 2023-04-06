import { app } from './app';

const PORT = 3000;

app.listen(PORT, () => {
    console.log(`Express with Typescript! http://localhost:${PORT}`);
});

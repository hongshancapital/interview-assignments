import { server } from './web';

const port = 5000;

server.listen({ port, host: '0.0.0.0' }, () => {
    console.log(`Server listening on port ${port}`)
});
import './pre-start'; // Must be the first import
import logger from 'jet-logger';

import EnvVars from './declarations/major/EnvVars';
import server from './server';


// **** Start server **** //

const msg = ('Express server started on port: ' + EnvVars.port.toString());
server.listen(EnvVars.port, () => logger.info(msg));

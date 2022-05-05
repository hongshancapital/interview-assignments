import ApiAppinstance from './api/ApiApp';
import MongdbInstance from './api/services/db/Mongoose';
import config from 'config';

MongdbInstance.connect();

(async () => {
    ApiAppinstance.start(config.get('app.port'));
})();

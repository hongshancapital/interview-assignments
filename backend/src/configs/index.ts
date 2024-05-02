import defaultConfigs from './default.config';
import devConfigs from './dev.config';
import testConfigs from './test.config';
import productionConfigs from './production.config';
import _ from 'lodash';

const NODE_ENV = process.env.NODE_ENV || 'dev';

let configs = defaultConfigs;

switch(NODE_ENV) {
  case 'dev':
    configs = _.merge(configs, devConfigs);
    break;
  case 'test':
    configs = _.merge(configs, testConfigs);
    break;
  case 'production':
    configs = _.merge(configs, productionConfigs);
    break;
}

export default configs;

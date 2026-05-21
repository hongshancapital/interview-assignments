import { Options } from "sequelize"; 
import devConfig from './db.config';

let config: Options;

// should have different config
if (process.env.NODE_ENV === 'development') {
  config = devConfig;
} else {
  config = devConfig;
}

export default config;

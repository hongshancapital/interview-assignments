import * as fs from 'fs';
import * as path from 'path';
import * as yaml from 'yaml';
import * as _ from 'lodash';

const configs = [
  path.resolve('./config.yaml'),
].filter(Boolean)
  .filter(fs.existsSync)
  .map((config) => fs.readFileSync(config, 'utf-8'))
  .map((value) => yaml.parse(value));


const config = [...configs].reduce((p, v) => _.merge(p, v));

export const getConfigValue = (key: string, defaultValue?: any) =>{
  return _.get(config, key, defaultValue);
} 

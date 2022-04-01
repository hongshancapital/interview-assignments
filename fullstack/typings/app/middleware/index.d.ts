// This file is created by egg-ts-helper@1.30.2
// Do not modify this file!!!!!!!!!

import 'egg';
import ExportErrorMiddleware = require('../../../app/middleware/errorMiddleware');
import ExportExecption from '../../../app/middleware/execption';
import ExportXssMiddleware = require('../../../app/middleware/xssMiddleware');

declare module 'egg' {
  interface IMiddleware {
    errorMiddleware: typeof ExportErrorMiddleware;
    execption: typeof ExportExecption;
    xssMiddleware: typeof ExportXssMiddleware;
  }
}

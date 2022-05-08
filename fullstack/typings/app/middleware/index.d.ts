// This file is created by egg-ts-helper@1.30.3
// Do not modify this file!!!!!!!!!

import 'egg';
import ExportAuthority from '../../../app/middleware/authority';
import ExportErrorHandler from '../../../app/middleware/error-handler';

declare module 'egg' {
  interface IMiddleware {
    authority: typeof ExportAuthority;
    errorHandler: typeof ExportErrorHandler;
  }
}

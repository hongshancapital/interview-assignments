// This file is created by egg-ts-helper@1.34.5
// Do not modify this file!!!!!!!!!
/* eslint-disable */

import 'egg';
import ExportUrl from '../../../app/model/url';

declare module 'egg' {
  interface IModel {
    Url: ReturnType<typeof ExportUrl>;
  }
}

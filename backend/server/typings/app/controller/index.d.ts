// This file is created by egg-ts-helper@1.34.5
// Do not modify this file!!!!!!!!!
/* eslint-disable */

import 'egg';
import ExportSiteController from '../../../app/controller/siteController';

declare module 'egg' {
  interface IController {
    siteController: ExportSiteController;
  }
}

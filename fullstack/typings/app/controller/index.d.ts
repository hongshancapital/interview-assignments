// This file is created by egg-ts-helper@1.30.2
// Do not modify this file!!!!!!!!!

import 'egg';
import ExportBase from '../../../app/controller/base';
import ExportHome from '../../../app/controller/home';
import ExportShortDomain from '../../../app/controller/shortDomain';

declare module 'egg' {
  interface IController {
    base: ExportBase;
    home: ExportHome;
    shortDomain: ExportShortDomain;
  }
}

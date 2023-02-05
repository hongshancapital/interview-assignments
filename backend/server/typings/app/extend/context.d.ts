// This file is created by egg-ts-helper@1.34.5
// Do not modify this file!!!!!!!!!
/* eslint-disable */

import 'egg';
import ExtendContext from '../../../app/extend/context';
type ExtendContextType = typeof ExtendContext;
declare module 'egg' {
  interface Context extends ExtendContextType { }
}
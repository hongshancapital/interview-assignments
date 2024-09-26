import { DEFAULT_CODE } from './errCode';

export const getErrCode = (err: any) : string => {

  if (typeof err === 'string') {
    return err;
  }

  // undefined Error type
  console.log(err);

  return DEFAULT_CODE;
};
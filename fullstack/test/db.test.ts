/** @format */

import mongoose from 'mongoose';
import { Db } from '../src/db';

it('test db connect error', async () => {
  mongoose.connect = jest.fn().mockImplementationOnce(() => {
    throw new Error('this is an error.');
  });
  console.error = jest.fn().mockImplementationOnce(() => {
    console.log('this is an error test');
  });
  expect(await Db.connectDB()).toBe(undefined);
});

it('test db connect unexpected  error', async () => {
  mongoose.connect = jest.fn().mockImplementationOnce(() => {
    throw undefined;
  });
  console.error = jest.fn().mockImplementationOnce(() => {
    console.log('this is an error test');
  });
  expect(await Db.connectDB()).toBe(undefined);
});

it('test db connect successed', async () => {
  mongoose.connect = jest.fn().mockImplementationOnce(() => {
    return;
  });
  await Db.connectDB();
  expect(mongoose.connect).toBeCalled();
});

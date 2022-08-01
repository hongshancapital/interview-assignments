/** @format */

import mongoose from 'mongoose';
import { connectDB } from '../src/db';

it('test db connect error', async () => {
  mongoose.connect = jest.fn().mockImplementationOnce(() => {
    throw new Error('this is an error.');
  });
  console.error = jest.fn().mockImplementationOnce(() => {
    console.log('this is an error test');
  });
  expect(await connectDB()).toBe(undefined);
});

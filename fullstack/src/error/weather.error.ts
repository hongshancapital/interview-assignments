import { MidwayError } from '@midwayjs/core';

export class WeatherEmptyDataError extends MidwayError {
  constructor(err?: Error) {
    super('weather data is empty', {
      cause: err,
    });
    if (err?.stack) {
      this.stack = err.stack;
    }
  }
}

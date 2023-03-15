/** @format */

import mongoose, { Schema } from 'mongoose';

/**
 * Url
 */
export default class Url {
  protected urlSchema: Schema;

  constructor() {
    this.urlSchema = new mongoose.Schema({
      urlCode: String,
      longUrl: String,
    });
  }

  /**
   *获取url model
   */
  getModel() {
    return mongoose.model('Url', this.urlSchema);
  }
}

/*
 * @Author: Json Chen
 * @Date: 2023-02-13 16:37:54
 * @LastEditors: Json Chen
 * @LastEditTime: 2023-02-13 18:17:36
 * @FilePath: /interview-assignments/fullstack/src/modules/short-url/entity.ts
 */
import { getModelForClass, modelOptions, prop } from "@typegoose/typegoose";
@modelOptions({
  schemaOptions: {
    timestamps: true,
    versionKey: false,
  },
})
export class ShortUrl  {
  @prop({
    required: true,
  })
  url: string;

  @prop({
    unique:true
  })
  shortId: string;
}

export const ShortUrlModel = getModelForClass(ShortUrl);
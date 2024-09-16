import { prop } from "@typegoose/typegoose";

export class ShortUrl {
  @prop({ type: String, unique: true })
  public shortUrl: string;

  @prop({ type: String })
  public longUrl: string;
}

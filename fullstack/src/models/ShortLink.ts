import mongoose from "mongoose";
const Schema = mongoose.Schema;

const shortLinkSchema = new mongoose.Schema(
  {
    urlId: {
      type: String,
      required: true,
      unique: true,
      index: true,
    },
    origUrl: {
      type: String,
      required: true,
    },
    shortUrl: {
      type: String,
      required: true,
    },
  },
  { timestamps: true }
);

const ShortLink = mongoose.model("ShortLink", shortLinkSchema);

export default ShortLink;

import mongoose from "mongoose";
const Schema = mongoose.Schema;

const shortLinkSchema = new mongoose.Schema(
  {
    urlId: {
      type: String,
      required: true,
    },
    origUrl: {
      type: String,
      required: true,
    },
    shortUrl: {
      type: String,
      required: true,
    },
    clicks: {
      type: Number,
      required: true,
      default: 0,
    },
  },
  { timestamps: true }
);

const ShortLink = mongoose.model("ShortLink", shortLinkSchema);

export default ShortLink;

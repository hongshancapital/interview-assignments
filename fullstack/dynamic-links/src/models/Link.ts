import { Document, model, Model, Schema } from 'mongoose';
import { nanoid } from 'nanoid';
import { AppSettings } from '../settings';

export interface ILink extends Document {
  keyword: string;
  url: string;
  createdAt: Date;
  updatedAt: Date;
}

interface ILinkModel extends Model<ILink> {
}

const schema = new Schema<ILink>({
  keyword: {
    type: String,
    index: true,
    unique: true,
    required: true,
    default: nanoid(AppSettings.MAX_KEYWORD)
  },
  url: {
    type: String,
    index: true,
    unique: true,
    required: true
  }
}, { timestamps: true });

const Link: ILinkModel = model<ILink, ILinkModel>('Link', schema);

export default Link;

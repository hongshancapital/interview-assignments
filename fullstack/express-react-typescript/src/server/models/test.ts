import { ILink } from "../domain/ILink";
import Database from '../dbConfigs';
import { Schema } from "mongoose";

const { mongo: { model } } = Database;

const TestSchema: Schema<ILink> = new Schema<ILink>({ longLink: { type: String, required: true }, shortLink: { type: String, required: true } });

export default model<ILink>('Test', TestSchema);


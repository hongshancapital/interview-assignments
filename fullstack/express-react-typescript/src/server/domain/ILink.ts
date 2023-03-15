import {Document} from "mongoose";

export interface ILink extends Document{
    longLink: string;
    shortLink: string;
}

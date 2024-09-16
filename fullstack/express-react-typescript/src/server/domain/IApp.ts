import {ILink} from "./ILink";

export interface AppStates {
    longLink: string,
    shortLink: string,
    longLinkText: string,
    shortLinkText: string,
    response?: ILink
}

export interface AppProps {}

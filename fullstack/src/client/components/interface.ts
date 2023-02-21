import { IUrl } from "../../server/interfaces/IUrl";
/**
 * url interface
*/
export interface DetailStates {
  inputRawUrl: string;
  inputUrlCode: string;
  shortenUrl: string;
  rawUrl: string;
  shortenMsg?: string;
  queryMsg?: string;
  rawUrls?: Array<IUrl>;
}

export interface DetailProps {}

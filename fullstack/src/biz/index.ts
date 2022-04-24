import { IError } from "../types";

export interface IShortUrlBiz {
  register(long: string): Promise<string | null | IError>;
  visit(short: string): Promise<string | null | IError>;
}


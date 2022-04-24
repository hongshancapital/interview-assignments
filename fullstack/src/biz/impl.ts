import { nanoid } from "nanoid";
import { IShortUrlBiz } from ".";
import { IDomainStoreRepo } from "../repo/domain-store";
import { IError } from "../types";

export class ShortUrlBiz implements IShortUrlBiz {
  constructor(private repo: IDomainStoreRepo) {}

  async register(long: string): Promise<string | null | IError> {
    const short = nanoid(8);
    try {
      this.repo.registerShortDomain(short, long);
      return short;
    } catch (err) {
      return null;
    }
  }

  async visit(short: string): Promise<string | null | IError> {
    try {
      const long = await this.repo.getLongDomain(short);
      return long;
    } catch (err) {
      return null;
    }
  }

}


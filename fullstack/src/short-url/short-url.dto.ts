import { IsNotEmpty } from 'class-validator';

export class LongUrlDTO {
  @IsNotEmpty()
  url: string;
  constructor(url?: string) {
    this.url = url;
  }
}

export class GetShortUrlParams {
  @IsNotEmpty()
  code: string;

  constructor(code?: string) {
    this.code = code;
  }
}

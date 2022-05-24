import { IsUrl } from 'class-validator';

export class CreateUrlRequest {
  @IsUrl()
  originalUrl: string;
}

export interface UrlResponse {
  shortUrl: string;
  originalUrl: string;
}

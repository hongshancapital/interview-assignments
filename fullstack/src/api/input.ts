import { ApiProperty } from '@nestjs/swagger';
import { IsUrl } from 'class-validator';

export class GetShortUrlInput {
  @IsUrl()
  @ApiProperty()
  originalUrl: string;
}

export class GetOriginalUrlInput {
  @IsUrl()
  @ApiProperty()
  shortUrl: string;
}

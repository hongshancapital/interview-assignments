import { ApiProperty } from '@nestjs/swagger';

export class GetShortUrlOutput {
  @ApiProperty()
  shortUrl: string;
}

export class GetOriginalUrlOutput {
  @ApiProperty()
  originalUrl: string;
}

import { ApiProperty } from '@nestjs/swagger';
import { IsUrl } from 'class-validator';

export class OriginUrl {
  @ApiProperty({
    description: '原始url',
    required: true,
    type: 'string',
    example: "https://www.baidu.com/page/1"
  })
  @IsUrl()
  originUrl: string;
}

export class ShortUrl {
  @ApiProperty({
    description: '端url',
    required: true,
    type: 'string',
    example: "https://short.url//"
  })
  @IsUrl()
  shortUrl: string
}
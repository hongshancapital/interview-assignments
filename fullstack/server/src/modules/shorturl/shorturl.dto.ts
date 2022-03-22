import { IsNotEmpty, IsString } from 'class-validator';
import { ApiProperty } from '@nestjs/swagger';

export class IVerifyLongUrl {
  @ApiProperty()
  @IsString({
    message: 'URL 为字符串！'
  })
  @IsNotEmpty({
    message: 'URL 不能为空!'
  })
  url: string;
}

export class IVerifyShortUrl {
  @ApiProperty()
  @IsString({
    message: '短 URL 为字符串！'
  })
  @IsNotEmpty({
    message: '短 URL 不能为空!'
  })
  s_url: string;
}

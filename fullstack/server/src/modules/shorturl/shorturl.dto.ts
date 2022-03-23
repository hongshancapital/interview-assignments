import { IsNotEmpty, IsString, IsUrl, MinLength } from 'class-validator';
import { ApiProperty } from '@nestjs/swagger';

export class IVerifyLongUrl {
  @ApiProperty()
  @IsString({
    message: 'URL 为字符串！'
  })
  @IsNotEmpty({
    message: 'URL 不能为空!'
  })
  @IsUrl({
    message: 'URL 格式不正确!'
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
  @MinLength(8, {
    message: 'URL 格式不正确!'
  })
  s_url: string;
}

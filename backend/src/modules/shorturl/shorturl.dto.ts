import { IsNotEmpty, IsString, IsUrl } from 'class-validator';
import { ApiProperty } from '@nestjs/swagger';

export class IVerifyUrl {
  @ApiProperty()
  @IsString({
    message: 'URL 为字符串！'
  })
  @IsUrl({}, {
    message: 'URL 格式不正确!'
  })
  @IsNotEmpty({
    message: 'URL 不能为空!'
  })
  @IsUrl({
    message: 'URL 格式不正确!'
  })
  url: string;
}

import { ApiProperty } from '@nestjs/swagger'
import { IsNotEmpty, IsUrl } from 'class-validator'

export class GetShortUrlDto {
  @ApiProperty({
    description: '需要转换为短链的 URL'
  })
  @IsNotEmpty()
  @IsUrl()
  url: string
}

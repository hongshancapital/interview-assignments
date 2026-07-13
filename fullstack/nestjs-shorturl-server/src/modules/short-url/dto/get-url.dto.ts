import { ApiProperty } from '@nestjs/swagger'
import { IsNotEmpty, IsUrl } from 'class-validator'

export class GetUrlDto {
  @ApiProperty({
    description: '短链 URL'
  })
  @IsNotEmpty()
  @IsUrl()
  shortUrl: string
}

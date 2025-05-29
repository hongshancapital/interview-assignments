import { ApiProperty } from '@nestjs/swagger'

export class GetShortUrlVo {
  @ApiProperty({
    description: '短链'
  })
  shortUrl: string

  @ApiProperty({
    description: '原链接'
  })
  url: string
}

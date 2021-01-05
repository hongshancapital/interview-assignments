import { ApiProperty } from "@nestjs/swagger";

export class ShortenDto {
  @ApiProperty({
    description: "The regular URL",
    default: "https://docs.nestjs.com/",
  })
  link: string;
}

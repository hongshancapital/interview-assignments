import { ApiProperty } from "@nestjs/swagger";

export class GenerateShortLinkDto {
  @ApiProperty()
  url: string;
}
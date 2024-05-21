import { Length, IsString } from "class-validator";

export class SearchShortDto {
  @IsString()
  @Length(4,8)
  shortKey: string;
}

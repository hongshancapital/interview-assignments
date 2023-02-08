import { IsUrl } from "class-validator";

export class CreateShortDto {
  @IsUrl()
  url: string;
}

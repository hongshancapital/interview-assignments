import { IsNotEmpty, IsString, Length } from 'class-validator';

export class GetReqDto {
  @IsNotEmpty()
  @Length(1, 8)
  @IsString()
  code: string;
}

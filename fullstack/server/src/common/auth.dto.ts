import { IsString } from 'class-validator';
import { ApiProperty } from '@nestjs/swagger';

export class TokenCredentials {
  @ApiProperty()
  @IsString()
  readonly id: string;

  @ApiProperty()
  @IsString()
  username: string;

  @ApiProperty()
  @IsString()
  nickName?: string;

  @ApiProperty()
  @IsString()
  role: string;
}

export class Credentials {
  @ApiProperty()
  @IsString()
  readonly username: string;

  @ApiProperty()
  @IsString()
  password: string;
}

export class IRegisterCredentials {
  @ApiProperty()
  @IsString()
  app_name: string;
  
  @ApiProperty()
  @IsString()
  company_name: string;

  @ApiProperty()
  @IsString()
  readonly username: string;

  @ApiProperty()
  @IsString()
  password: string;
}

export class UserCredentials {
  @ApiProperty()
  @IsString()
  readonly session_key?: string;

  @ApiProperty()
  @IsString()
  id?: string;

  @ApiProperty()
  @IsString()
  nickName?: string;

  @ApiProperty()
  @IsString()
  role: string;

  @ApiProperty()
  @IsString()
  phone?: string;
}

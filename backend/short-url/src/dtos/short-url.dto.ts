import { IsUrl, IsInt, IsOptional } from 'class-validator';

export class CreateShortUrlDto {
    @IsUrl()
    public longUrl: string;

    @IsOptional()
    @IsInt()
    public expiredAt?: number;
}

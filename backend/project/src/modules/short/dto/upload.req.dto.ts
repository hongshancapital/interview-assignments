import { IsNotEmpty, IsString, Length, Matches } from 'class-validator';

export class UploadReqDto {
  @IsNotEmpty()
  @IsString()
  @Length(1, 1000)
  @Matches(
    /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/,
    {
      message: '请输入正确的网址',
    },
  )
  url: string;
}

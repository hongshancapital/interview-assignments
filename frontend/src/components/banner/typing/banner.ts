export interface IBannerImage {
  image: String;
  des: String;
  backgroundColor: String;
}
export interface ICustomConfig {
  stayTime?: number,
  rollTime?: number
}
export interface IDefaultConfig {
  stayTime: number,
  rollTime: number
}

export interface IProps {
  data: IBannerImage[];
  config? :ICustomConfig;
  current?: number;
  stayTime?: number;
}

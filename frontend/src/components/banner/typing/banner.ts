interface ITitle {
  color?: string;
  big?: string;
  small?: string;
}


export interface IBannerImage {
  image: String;
  des: String;
  titles: ITitle;
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
export interface IStyle {
  backgroundColor?: String;
}
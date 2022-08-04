export interface IConfig {
  title: string[];
  fontColor: string;
  backgroundImage: string;
  content: string[];
}

export interface ICarousel {
  config: IConfig[];
  interval: number;
}
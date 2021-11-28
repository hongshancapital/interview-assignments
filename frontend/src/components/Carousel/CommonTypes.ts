export type IMgscrs = string[];

export interface ICarouselProps {
  imgSrcs: IMgscrs;
  autoDuration: number;
  duration: number;
  width: number;
  height: number;
}

export interface IForwardRef {
  switchTo: (i: number) => void;
}

export interface IImgConProps {
  imgSrcs: IMgscrs;
  duration: number;
  imgWidth: number;
  imgHeight: number;
}

export interface ISwitchProps {
  duration: number;
  dotNum: number;
  curIndex: number;
  onChange?: (i: number) => void;
}

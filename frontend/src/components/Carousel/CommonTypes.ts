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
  dotNum?: number;
  curIndex: number;
  imgSrcs: IMgscrs;
  onChange?: (i: number) => void;
}
export interface IImgProps {
     url:string,
     id:string
}

export  interface ListenObj {
  [eventName: string]: (( (e: any) => void) | null)[];
}
export interface ListenIndex {
  eventName: string;
  index: number;
}
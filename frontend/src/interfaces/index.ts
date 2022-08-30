export interface PageItem {
    title: string[];
    subtitle?: string[];
    icon: imageType | any;
    fontColor: string;
    imageSrc: string;
    backgroundColor?: string;
}

export enum imageEnum {
    iphone = "iphone",
    tablet = "tablet",
    airpods = "airpods",
}

export type imageType = imageEnum.iphone | imageEnum.tablet | imageEnum.airpods;

export interface AnimationProps {
  animationDuration: string;
  animationMoveDuration: string;
  activeIndex: number;
  onAnimationEnd?: Function;
}

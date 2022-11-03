export interface CarouselItemProps {
  title: string | string[];
  text?: string | string[];
  panelStyle?: React.CSSProperties;
  goodsImgStyle: React.CSSProperties;
};

export type CarouselList = Array<CarouselItemProps & { key: string }>;
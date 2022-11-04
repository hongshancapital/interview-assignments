export interface CarouselItemProps {
  title: string | string[];
  text?: string | string[];
  panelStyle?: React.CSSProperties;
  goodsImgStyle: React.CSSProperties;
};

export type CarouselList = Array<CarouselItemProps & { key: string }>;

export interface CarouseBarProps {
  currentIndex: number;
  isActive: boolean;
  interval: number,
  onClick: (index: number) => void
};

export interface CarouseProps {
  list: CarouselList;
  interval?: number;
};

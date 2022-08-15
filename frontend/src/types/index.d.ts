declare type CarouselItem = {
  background: string;
  color?: string;
  title: string | string[];
  desc?: string | string[];
  pic: string;
}

declare type CarouselProps = {
  duration?: number;
  data: CarouselItem[];
};

declare type ProgressProps = {
  duration?: number;
  width?: number;
  index:number;
  data: CarouselItem[];
};


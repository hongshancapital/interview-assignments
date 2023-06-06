declare namespace carousel {
  interface CarouselItem {
    title: string[];
    desc?: string[];
    imgUrl: string;
    color?: string;
    bgColor?: string;
  }
  interface CarouselItemProps extends CarouselItem {
    // next: () => void;
    isActive: boolean;
  }
  interface CarouselProps {
    duration?: number; // 轮播持续时长 默认3000ms
    list: CarouselItem[]; // 轮播列表
    autoPlay?: boolean; // 是否自动轮播 默认true
    showIndicator?: boolean; // 是否显示轮播点 默认true
    className?: string; // 轮播容器类名
  }
}
export = carousel;
export as namespace carousel;

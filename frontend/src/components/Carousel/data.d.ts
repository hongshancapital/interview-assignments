/*
* 轮播图每一项组件的属性
* */
export interface ICarouselItem {
    width?: number | string;
    className?: string;
    title: string;
    subTitle?: string;
    pic: string;
}

/*
* 轮播图组件的属性
* */
export interface CarouselProps {
    items: ICarouselItem[];
    speed?: number;
    duration?: number;
}

/*
* 指示器每一项组件的属性
* */
export interface IIndicatorItemProps {
    active: boolean;
    duration: number;
}

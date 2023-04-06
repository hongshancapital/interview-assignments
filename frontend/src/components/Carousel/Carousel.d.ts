// 轮播图组件参数
export type CarouselProps = React.PropsWithChildren<{
    carouselList: CarouselItem[] | [];
    intervalTime?: number;
    height?: number;
    width?: number;
}>;

// 轮播图单元
export type CarouselItem = {
    title?: string[];
    description?: string[];
    imageURL: string;
};

// 轮播图宽高样式处理
export type CarouselStyle = {
    height?: string;
    width?: string;
};
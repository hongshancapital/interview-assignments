export interface ICarouselProps {
    hasIndicator?: boolean;
    indicator?: any;
    delay?: number;
    data: any[];
}

export interface ICarouselIndicatorProps {
    num: number;
    selectIndex: number
}


export interface ICarouselSlideProps {
    selectIndex: number;
    data: any[];
}

export interface ICarouseData {
    path: string;
    title: string;
    content?: string;
    fontColor: string;
}
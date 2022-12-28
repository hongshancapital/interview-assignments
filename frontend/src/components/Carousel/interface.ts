import { ReactNode } from "react";

export interface CarouselData { title?: string, content?: string, imgUrl?: string, [key: string]: any };
export interface CarouselProps {
    dataSource: Array<CarouselData>;
    autoPlay?: any;
    showSwitch?: boolean;
    children?: ReactNode;
}
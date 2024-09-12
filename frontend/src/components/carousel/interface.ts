import { ReactNode } from "react";

export interface ICarousel {
    autoplay?: boolean;
    children?: ReactNode | ReactNode[];
    afterChange?: (current: number) => void;
    className?: string;
}

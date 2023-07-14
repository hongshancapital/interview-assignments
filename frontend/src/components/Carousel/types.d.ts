export interface CarouSelProps {
    autoPlay?: boolean;

    /**
     * @abstract slides automatic switching time interval (in seconds)
     * @default 5
     */
    autoPlayInterval?: number

    /**
     * @abstract slides transition duration (in seconds)
     * @default 1
     */
    transitionDuration?: number
}

export type CarouselContextType = {
    activedIndex: number;
    slidesCount: number
} & CarouSelProps
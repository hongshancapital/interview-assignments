
export interface CarouselProps {
    children?: React.ReactChild[];
    className?: string;
    infiniteLoop?: boolean;
    interval?: number;
    showIndicators?: boolean;

    onClickItem?: (index: number, item: React.ReactNode) => void;
    onChange?: (index: number, item: React.ReactNode) => void;
    onSwipeStart?: (event: React.TouchEvent) => void;
    onSwipeEnd?: (event: React.TouchEvent) => void;
    onSwipeMove?: (event: React.TouchEvent) => boolean;
}
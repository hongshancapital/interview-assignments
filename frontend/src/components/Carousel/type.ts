export interface CarouselConfigProps {
    title: string[],
    description: string[],
    img: string,
    fontColor: string,
    bgColor: string,
    bgHeight: number,
}

export interface CarouselProps {
    interval?: number,
    children: JSX.Element[],
}
export interface CarouselItem {
    textList: string[],
    bgColor: string,
    color: string,
    sub: string[],
    img: string,
    size: string
}

/**
 * scrollDirection:page scroll horizontal || vertical
 * initialPage: Carousel init page index. default 0
 * children: carousel contents
 */
export interface ICarouselProps {
    scrollDirection: 'row' | 'column',
    initialPage: number,
    config: CarouselItem[]
}
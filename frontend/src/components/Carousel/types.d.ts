import React from "react"

/**
 * scrollDirection:page scroll horizontal || vertical
 * onPageChanged: when page has changed.this event will trigger
 * initialPage: Carousel init page index. default 0
 * children: carousel contents
 */
export interface ICarouselProps {
    scrollDirection: 'row' | 'column',
    onPageChanged: (currentIndex: number) => void
    initialPage: number,
    children: Array<React.ReactElement>
}

export interface animateObj extends HTMLDivElement {
    timer?: NodeJS.Timer
}
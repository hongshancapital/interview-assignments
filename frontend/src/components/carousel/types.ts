import { DOMAttributes, CSSProperties, ReactNode } from 'react';


export type CarouselProps = {
    children: ReactNode[]
    interval: number
    // markPosition?: 'bottom' | 'left' | 'right'

    className?: string
    style?: CSSProperties
} & DOMAttributes<HTMLDivElement>


export type CarouselBaseState = {
    curIndex: number
    status: CarouselStatus
    listLen: number
}
type CarouselStatus = 'loop' | 'leave' | 'hover'


export type CarouselMemoElsResponse = {
    items: ReactNode[]
    containerWidth: number
}

export type CarouselRecordStatus = {
    timer: ReturnType<typeof setInterval> | number
    loopStartTime: number
    hoverStartTime: number
}

export type MarksProps = {
    curIndex: number
    listLen: number
   
}

export type MemoState = {
    items: ReactNode[]
    containerWidth: number
}

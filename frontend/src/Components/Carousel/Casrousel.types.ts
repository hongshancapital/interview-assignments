import React, { Key } from "react";
// Casrousel入参 
export interface CasrouselProps {
    casrouselList: CasrouselList[]
    activeIndex?: number | undefined
    isAuto?: boolean
    wait?: number
}
// 外层Casrousel的循环结构
export interface CasrouselList {
    id: Key
    title: string | React.ReactElement
    titleStyles: {
        color: string
    }
    subText?: string | React.ReactElement
    subTextStyles?: {
        color: string
    }
    casrouselImage: string
}

export interface CasItemProps {
    casrouseInfo: CasrouselList
}

// Carousel Bar的type定义
export interface CasrouselBarProps {
    activeIndex: number
    barLen: number
    wait: number
    autoConfig: boolean
    onMouseEnter: () => void
    selectIndex: (index: number) => void
}
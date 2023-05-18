import { ReactNode } from "react"

export enum Theme {
    BlackTheme,
    WhiteTheme,
    GrayTheme
}

export interface Info {
    title: string
    tips?: string
}

export interface LayoutProps{
    theme: Theme
    info: Info
    image: ReactNode
}

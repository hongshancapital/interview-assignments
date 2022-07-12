/// <reference types="react-scripts" />

declare enum Theme {
  dark = 'dark',
  light = 'light',
}

declare interface RotationItem {
  title?: string
  subTitle?: string
  image?: string
  theme?: Theme
  color?: string
  [key: string]: any
}

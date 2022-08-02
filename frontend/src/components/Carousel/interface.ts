import { ReactNode, ReactElement } from 'react'
import { INativeProps } from '../../interface/naitve'

export type TCarouselItem = {} & INativeProps
export type TCarouselRef = {
  moveTo: (index: number) => void
  moveToNext: () => void
  moveToPrev: () => void
}
export type TCarouselItemRef = {
  onClick: () => void
}
export interface ICarouselProps extends INativeProps {
  defaultIndex?: number
  autoPlay?: boolean
  delay?: number
  speed?: number
  loop?: boolean
  slideSize?: number
  trackOffset?: number
  stuckAtBoundary?: boolean
  rubberband?: boolean
  indicator?: (total: number, currIndex: number) => ReactNode
  onIndexChange?: (index: number) => void
  children: ReactElement | ReactElement[]
}

export type IIndicatorProps = {
  total: number
  currIndex: number
  delay?: number
  onClick: (item: any) => void
} & INativeProps<'--delay'>

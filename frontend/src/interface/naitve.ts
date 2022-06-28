import type { CSSProperties, ReactElement } from 'react'
export interface INativeProps<S extends string = never> {
  className?: string
  style?: CSSProperties & Partial<Record<S, string>>
  tabIndex?: number
  children?: ReactElement | ReactElement[]
}

import React, { FC } from 'react'
import "./index.css";

// 类名统一前缀
const classPrefix = `my-carousel`

type Props = {
  // onClick?: (e: React.MouseEvent<HTMLDivElement, MouseEvent>) => void
  children?: React.ReactNode
}

export const CarouselItem: FC<Props> = props => {
  return (
    <div
    className={`${classPrefix}-item`}
    // onClick={props.onClick}
    >
      {props.children}
    </div>
  )
}
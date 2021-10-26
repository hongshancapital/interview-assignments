import React from 'react'
import classes from "./carousel.module.scss"

type CarouselViewportProps = {
  viewport: number[]
  children: JSX.Element[]
}

/**
 * 轮播图虚拟化的基础组件
 * 接收一个number[](viewport) 以及一个元素的数组(JSX.Element[])
 * 返回根据number[]序号组合的元素数组
 * 例如一个有100个元素的轮播图， 为了实现轮播效果，实际实际渲染的组件数目最少只需要2个。
 * @param param0 
 * @returns 
 */
export const CarouselViewport = ({
  viewport,
  children,
}: CarouselViewportProps) => {
  return (
    <>
      {viewport.map((x, i) => {
        return (
          <CarouselElement key={i}>
            {children[x]}
          </CarouselElement>
        )
      })}
    </>
  )
}

const CarouselElement = ({
  children,
}: {
  children: JSX.Element
}) => {
  const c = children.props.className || ""
  return React.cloneElement(children, {
    className: c ? `${c} ${classes.card}` : classes.card,
  })
}


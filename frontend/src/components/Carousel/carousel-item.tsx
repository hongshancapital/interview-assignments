import React, { forwardRef, useImperativeHandle } from 'react'
import Styles from './styles.module.css'
import { TCarouselItem, TCarouselItemRef } from './interface'
export default forwardRef<TCarouselItemRef, TCarouselItem>((props, ref) => {
  const onClick = () => {}
  useImperativeHandle(ref, () => ({
    onClick
  }))
  return (
    <>
      <li
        data-testid='CarouselItem'
        {...props}
        className={`${Styles.CarouselItem} ${props.className}`}
      >
        {props.children}
      </li>
    </>
  )
})

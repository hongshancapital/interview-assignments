import React, { FC, Fragment } from 'react'

export interface CarouselSlideProps {
  key: string | number
  children: React.ReactNode
}

export const CarouselSlide: FC<CarouselSlideProps> = (props) => {
  return <Fragment>{props.children}</Fragment>
}

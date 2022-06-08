import React, { ReactChild } from "react"

export const CarouselSlide = ({ children }: {
  children: ReactChild | ReactChild[]
}) => {
  return (
    <>
      {children}
    </>
  )
}

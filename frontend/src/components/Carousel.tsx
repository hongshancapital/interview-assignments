import React, { useEffect, useState } from "react";
require('../assets/style/Carousel.css')

interface CarouselProps {
  pages: pageItem[]
  delay: number
  enableManualSwitch: boolean
}

export type pageItem = {
  img: string
  title: string
  subtitle: string
}

export function Carousel(props: CarouselProps): JSX.Element {
  const { pages, delay, enableManualSwitch } = props
  const [page, setPage] = useState(-1)
  const carouselWrapperRef = React.useRef<HTMLDivElement>(null)
  const carsouelIndicator = React.useRef<HTMLDivElement>(null)

  let timer: any
  const transUnit = pages.length && 1 / pages.length
  
  const toPageAuto = (): void => {
    timer = setTimeout(() => {
      const nextPage = (page + 1) % pages.length
      toPage(nextPage)
    }, page === -1 ? 0 : delay)
  }

  const toPage = (nextPage: number): void => {
    clearTimeout(timer)
    setPage(nextPage)
  }

  useEffect(() => {
    toPageAuto()
  },)
  
  return (
    <div className="carousel">
      <div className="carousel__wrapper" style={{ transform: `translate(${-page * transUnit * 100}%, 0)`}} ref={carouselWrapperRef}>
        {
          pages.map((item: pageItem) => (
            <div className="wrapper__page" key={item.title}>
              <div className="page__title">
                <p className="title">{item.title}</p>
                <p className="subtitle">{item.subtitle}</p>
              </div>
              <img className="page__icon" src={item.img} alt="" />
            </div>
          ))
        }
      </div>
      <div className="carsousel__indiactor" ref={carsouelIndicator}>
        {
          pages.map((_, index: number) => (
            <div className="indiactor__bar" style={{pointerEvents: `${enableManualSwitch ? 'auto' : 'none'}`}} onClick={() => {
              enableManualSwitch && page !== index && toPage(index)
            }} key={_.title}>
              <div className="bar__fill" style={page === index ? {transition: 'width 3s ease-in-out', width: '100%'} : {}}></div>
            </div>
          ))
        }
      </div>
    </div>
  )
}

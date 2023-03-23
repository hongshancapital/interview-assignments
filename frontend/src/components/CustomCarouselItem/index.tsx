import { useMemo } from 'react'
import { ICarouselData } from 'src/type'
import './style.css'

export interface ICarouselItemProps {
  data: ICarouselData
  color: string
}

export default function CarouselItem(props: ICarouselItemProps) {
  const { data, color } = props

  return useMemo(() => {
    return (
      <div
        className="carousel-item"
        style={{
          backgroundImage: `url(${data.icon})`,
          color,
        }}
      >
        <div className="title">{data.title}</div>
        <div className="text">{data.subtitle}</div>
      </div>
    )
  }, [data, color])
}

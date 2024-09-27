import React, { useMemo } from "react"
import "./index.css"
import useIndex from "./useIndex"

export interface CarouselType {
  id: number | string
  imgUrl: string
  title: string[]
  description: string[]
  color: string
}

export interface CarouselProps {
  list: CarouselType[]
  interval?: number
  height?: string
  width?: string
}

function Carousel(props: CarouselProps) {
  const { list, interval = 3000, height, width = "100%" } = props
  const current = useIndex(interval, list.length)

  const style = useMemo(() => {
    // get width and unit
    const unitReg = /([0-9]+)((?<=[0-9])[^0-9]+)/gi
    const resultW = unitReg.exec(width)
    return {
      width: parseFloat(resultW?.[1] ?? "100"),
      unit: resultW?.[2] ?? "%",
    }
  }, [width])

  return (
    <div className="carousel-wrap" style={{ height, width }}>
      <div
        className="carousel"
        style={{
          transform: `translateX(-${current * style.width}${style.unit})`,
        }}
      >
        <CarouselList list={list} width={style.width} unit={style.unit} />
      </div>

      <div className="carousel-indicator">
        {list.map((v, i) => {
          return (
            <i
              key={i}
              className={`indicator-item ${current === i && "active"}`}
              style={{ transitionDuration: `${interval}ms` }}
            ></i>
          )
        })}
      </div>
    </div>
  )
}

interface CarouselListProps {
  list: CarouselType[]
  width: number
  unit: string
}

const CarouselList = React.memo((props: CarouselListProps) => {
  const { list, width, unit } = props
  return (
    <>
      {list.map((v, index) => {
        return (
          <div
            className="carousel-item"
            style={{
              left: `${index * width}${unit}`,
              color: v.color,
            }}
            key={v.id}
          >
            <img src={v.imgUrl} alt="" className="img" />
            {v.title.map((text, i) => {
              return (
                <h1 className="carousel-title" key={i}>
                  {text}
                </h1>
              )
            })}
            {v.description.map((desc, index) => (
              <div className="carousel-desc" key={index}>
                {desc}
              </div>
            ))}
          </div>
        )
      })}
    </>
  )
})
export default Carousel

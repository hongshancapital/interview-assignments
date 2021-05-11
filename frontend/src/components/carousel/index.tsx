import React, {useState,useEffect, useRef} from 'react'
import { propsType } from './interface'
import './style.css'

const Index = (props:propsType) => {
  const {
    carouselList,
    speed = 3000
  } = props;

  // 目前第几张图
  const [left, setLeft] = useState(0)
  // 进度条的宽度
  const [lineWidth, setLineWidth] = useState(0)
  // 为了获取当前盒子的宽度
  const carouselRef = useRef(null)
  const carouselWitdh:any = carouselRef?.current
  const clientWidth = carouselWitdh?.clientWidth
  
  useEffect(() => {
    if(!carouselRef) return
    setLineWidth(50)
    const interval = setInterval(() => {
      setLineWidth(0)
      let location = left + 1
      if(carouselList.length === location) {
        location = 0
      }
      setLeft(location)
      setLineWidth(50)
    }, speed)

    return () => {
      clearInterval(interval)
    }
  }, [left, carouselRef,carouselList,speed])

  return (
    <div className="box" ref={carouselRef}>
      <div className="carousel" >
        {
          carouselList.map((item) => {
            return (
              <div
                className="carousel-box"
                style={{
                  left: `-${left * (clientWidth || 0)}px`,
                  backgroundImage: `url(${item.img})`
                }}
              >
                <div className="carousel-font">
                  {item.font.map((font) => {
                    return <div
                      style={{
                        color: font.color,
                        fontSize:font.fontSize,
                        marginTop: '2px'
                      }}
                    >
                      {font.text}
                    </div>
                  })}
                </div>
              </div>
            )
          })
        }
      </div>
      <div className="carousel-line" style={{marginLeft: `-${carouselList.length * 56 / 2}px`}}>
        {
          [...Array(carouselList.length)].map((line, index) => {
            return (
              <div className="carousel-line-item">
                {index === left && (
                  <div
                    className="carousel-line-item-shade"
                    style={{
                      width: `${lineWidth}px`,
                      transition: `width ${speed / 1000}s linear`
                    }}
                  />
                )}
              </div>
            )
          })
        }
      </div>
    </div>
  )
}

export default Index
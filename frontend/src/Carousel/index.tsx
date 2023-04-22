import { useEffect, useState } from 'react'
import style from './style.module.scss'
import Indicator from './indicator'
export interface CarouselProps {
  swiperList: {
    title: string
    text?: string
    img: string
  }[]
}

export default function Carousel({ swiperList }: CarouselProps) {
  const [current, setCurrent] = useState(0)
  const duration = 3000
  const handleCurrentTab = () => {
    setCurrent(current >= swiperList.length - 1 ? 0 : current + 1)
  }
  useEffect(() => {
    let interval = setInterval(() => {
      handleCurrentTab()
    }, duration)
    return () => clearInterval(interval)
  })
  return (
    <div className={style.swiper}>
      {swiperList.map((el, index) => (
        <div
          key={el.title}
          className={style.item}
          style={{
            backgroundImage: `url(${el.img})`,
            left: `${100 * (index - current)}%`,
          }}
        >
          <div className={style.text}>
            <h1>{el.title}</h1>
            {el.text && <p>{el.text}</p>}
          </div>
        </div>
      ))}
      <Indicator
        total={swiperList.length}
        current={current}
        duration={duration}
        handleCurrentTab={handleCurrentTab}
      />
    </div>
  )
}

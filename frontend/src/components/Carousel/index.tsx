/* *
 * @author Ethan
 * @email 271757749@qq.com
 * @datetime  2023/04/19
 * */

import { type FC, type CSSProperties, useEffect, useState } from 'react'
import './index.css'

import { CarouselDataItem } from '../../App'

export interface CarouselProps {
  items: CarouselDataItem[]
  delay?: number
  autoPlay?: boolean
}

const Carousel: FC<CarouselProps> = ({ items, delay = 3000, autoPlay = true}) => {
  const [active, setActive] = useState<number>(0)
  const [auto,setAuto] = useState<boolean>(autoPlay)

  useEffect(() => {
    if (active >= items.length) {
      setActive(0)
      return
    }
    const timer = setTimeout(() => setActive((active + 1) % items.length), delay)
    if(!auto){
      clearTimeout(timer)
    }
    return () => clearTimeout(timer)
  }, [items, delay, active, auto])

  return (
    <div
      className="carousel"
      style={{ '--indicator-animation-duration': `${delay}ms` } as CSSProperties}
    >
      <ul className="carousel-wrapper" style={{ transform: `translateX(-${active * 100}%)` }}>
        {items.map((item, idx) => (
          <li
            key={item.id}
            className={`${idx === active ? 'carousel-item active' : 'carousel-item'}`}
          >
            <div
                className="container"
                style={{
                  backgroundColor: item.backgroundColor,
                  backgroundImage: `url(${item.bg})`
                }}
              >
                <div className="text-content" style={{ color: item.color }}>
                  <h2 className="title">{item.title?.join('\r\n')}</h2>
                  {item.contents && <h4 className="content">{item.contents.join('\r\n')}</h4>}
                </div>
              </div>
          </li>
        ))}
      </ul>
      <ul className="indicators">
        {items.map((item, idx) => (
          <li
            key={item.id}
            className={`${idx === active ? 'indicator active' : 'indicator'}`}
            onClick={() => {
              setAuto(false)
              setActive(idx)
             }
            }
            onMouseLeave={()=>{
              setAuto(true)
            }}
          >
            <span className="indicator__track">
              <span className={auto ? "indicator__bar__auto": "indicator__bar"}/>
            </span>
          </li>
        ))}
      </ul>
    </div>
  )
}

export default Carousel

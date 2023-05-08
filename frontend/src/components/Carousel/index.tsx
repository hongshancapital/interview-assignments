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
}

const Carousel: FC<CarouselProps> = ({ items, delay = 3000}) => {
  const [active, setActive] = useState(0)
  useEffect(() => {
    const timer = setTimeout(() => setActive((active + 1) % items.length), delay)
    return () => clearTimeout(timer)
  }, [items, delay, active])

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
                  {item.title && <h2 className="title">{item.title.join('\r\n')}</h2>}
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
            className={`indicator${idx === active ? ' active' : ''}`}
            onClick={() => {
              setActive(idx)
             }
            }
          >
            <span className="indicator__track">
              <span className="indicator__bar"/>
            </span>
          </li>
        ))}
      </ul>
    </div>
  )
}

export default Carousel

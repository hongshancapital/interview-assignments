
import React, { useCallback, useEffect, useRef, useState } from "react"
import './index.css';

export type Card = {
  title?: string[],
  desc?: string[],
  style?: {
    [x: string]: string
  },
}

const Carousel = ({ time = 3000, auto = true, lists = [] }: { time?: number, auto?: boolean, lists: Card[] }) => {
  const content = useRef<HTMLDivElement>(null)

  const timer = useRef<NodeJS.Timeout | null>(null)
  const current = useRef<number>(0)
  const [isActive, setIsActive] = useState(0)

  const transTo = useCallback(
    (index: number) => {
      (content.current as HTMLDivElement).style.transform = `translateX(-${index * 100}%)`;
      current.current = index
      setIsActive(index)
    }, [])


  useEffect(() => {
    if (!auto) return
    if (timer.current) clearInterval(timer.current);
    timer.current = setInterval(() => {
      current.current += 1
      setIsActive(isActive + 1)
      if (current.current >= lists.length) {
        current.current = 0
        setIsActive(0)
      }
      transTo(current.current)
    }, time || 2000)
    return () => {
      if (timer.current) {
        clearInterval(timer.current)
      }
    }
  }, [auto, isActive, lists.length, time, transTo])

  return <div
    className="carousel"
  >
    <div className="progress">
      {lists.map((item, index: number) => {
        return <span
          key={index}
          className={index === isActive ? 'active' : ''}
          onClick={() => transTo(index)}>
          {index === isActive && <i className="bar" style={{ animationDuration: `${time}ms` }}></i>}
        </span>
      })}
    </div>
    <div className="content" ref={content}>
      {lists.map((item, index: number) => {
        if (!item.title && !item.desc) {
          return ''
        }
        return <div className="box"
          key={index}
        >
          <div className="inner" style={item.style || {}}>
            <div className="title">
              {item.title?.map((jtem, jndex: number) => <p className="bold fz-50" key={jndex}>{jtem}</p>)}
              {item.desc?.map((jtem, jndex: number) => <p className="fz-32" key={jndex}>{jtem}</p>)}
            </div>
          </div>
        </div>
      })}
    </div>
  </div>
}



export default Carousel
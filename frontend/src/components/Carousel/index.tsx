import { useRef, useEffect, useState } from 'react'

import style from './index.module.scss'
import Progress from './module/progress'
import Card from './module/Card'

import { scrollVelocity } from '@/utils'

let timeID: unknown
let i: number = 0
let j = 0

export default function Carousel({
  list,
  showIndicatorBar
}: {
  list: { [k: string]: unknown }[]
  showIndicatorBar: boolean
}) {
  const liDom = useRef<HTMLLIElement>(null)
  const [progressValue, setProgressValue] = useState(0)
  const [active, setActive] = useState(0)

  useEffect(() => {
    init()
  }, [])

  const max = 100

  function init() {
    const parentElement = liDom.current?.parentElement as HTMLElement
    const distance = liDom.current?.offsetWidth

    timeID = setInterval(() => {
      i++
      setProgressValue(i)
      if (i === max) {
        i = 0
        j++
        setProgressValue(0)
        if (j == list.length) {
          j = 0
        }
        setActive(j)
        scrollBox(parentElement, distance!)
      }
    }, 20)
  }

  function scrollBox(target: HTMLElement, distance: number) {
    scrollVelocity(10, target, distance * j)
    // let timeId: unknown = null
    // let value = 10
    // if (target.scrollLeft == distance * (list.length - 1)) {
    //   let index = target.scrollLeft / 10
    //   timeId = setInterval(() => {
    //     value--
    //     target.scrollLeft -= index
    //     if (value <= 0) {
    //       target.scrollLeft = 0
    //       clearInterval(timeId as number)
    //     }
    //   }, 30)
    // } else {
    //   let index = (distance * j - target.scrollLeft) / 10
    //   timeId = setInterval(() => {
    //     value--
    //     target.scrollLeft += index
    //     if (value <= 0) {
    //       target.scrollLeft = distance * j
    //       clearInterval(timeId as number)
    //     }
    //   }, 30)
    // }
  }

  function clickIndicator(index: number) {
    i = 0
    j = index
    setActive(j)
    setProgressValue(0)
    clearInterval(timeID as number)
    const parentElement = liDom.current?.parentElement as HTMLElement
    const distance = liDom.current?.offsetWidth
    scrollBox(parentElement, distance!)
  }

  function _render() {
    if (list.length) {
      return (
        <ul className={style.ca}>
          {list.map((item, index) => (
            <li key={index} className={style.item} ref={liDom}>
              <Card
                title={item.title}
                dark={item.dark}
                description={item.description}
                img={item.img}
              />
            </li>
          ))}
        </ul>
      )
    }
    return (
      // 如果需要兜底, 使用兜底设计, 如果没有 返回 null
      <div>如果需要兜底, 使用兜底设计, 如果没有 返回 null</div>
    )
  }

  return (
    <div className={style.carousel}>
      {_render()}
      {list.length && showIndicatorBar ? (
        <div
          className="indicator"
          onMouseEnter={() => clearInterval(timeID as number)}
          onMouseLeave={() => init()}
        >
          {list.map((_, index) => (
            <div
              className="indicator-bar"
              key={index}
              onClick={() => clickIndicator(index)}
            >
              <Progress
                max={max}
                value={index === active ? progressValue : 0}
              />
            </div>
          ))}
        </div>
      ) : null}
    </div>
  )
}

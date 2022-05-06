import React, { useEffect, useState, useRef, } from 'react'
import { Options } from './Options'
import Slider from './components/Slider'
import Panel from './components/Panel'
import './index.css'

interface Props {
  delay?: number,
  duration?: number,
  data: Array<Options>
}

function sleep(ms: number) {
  return new Promise(resolve => setTimeout(resolve, ms))
}

function Carousel(props: Props): React.ReactElement {
  const { data, duration = 9000 } = props
  // 记录当前轮播图的索引，后面根据这个值来计算panel-wrap元素偏移量（使用百分比）
  const [index, setIndex] = useState<number>(0)
  const [runing, setRuning] = useState<boolean>(false)
  const ref = useRef<HTMLDivElement>(null)

  const total = data.length
  const interval = (duration / total) - 500

  const run = async (index: number) => {
    if (!ref.current || total <= 1 && !runing) {
      return
    }

    let curIndex = index
    // 如果轮播到尾部，则马上回到头部，并且开始新的一轮循环
    const isEnd = index >= total
    if (isEnd) {
      curIndex = 0

    }
    curIndex++
    setIndex(curIndex)
    const offsetX = curIndex === total ? '0%' : `${(- curIndex) * 100 / total}%`
    console.log('run', { index, curIndex, offsetX })
    const style = ref.current.style
    ref.current.style.transition = 'transform 0.5s linear 0s'
    await sleep(interval) // 等待interval毫秒
    style.transform = `translateX(${offsetX})`
    requestAnimationFrame(() => run(curIndex))
  }

  useEffect(() => {
    run(index)
    setRuning(true)
  }, [data])

  const style = { width: `${total * 100}%` }  // 设置容器和每一帧的宽度，容器的宽度 = 帧宽 * 帧数，使用百分比计算
  return (
    <div className="carousel">
      <div className="panel-wrap" ref={ref} style={style}>
        {
          data.map((item, index) => {
            return <Panel key={index} data={item} />
          })
        }
      </div>
      <Slider count={total} current={index} />
    </div>
  )
}

export default React.memo(Carousel)

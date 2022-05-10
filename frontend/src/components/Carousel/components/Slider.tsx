import React from 'react'

interface Props {
  count: number,
  current: number,
  duration: number
  onTransitionEnd: React.TransitionEventHandler<HTMLDivElement>
}

function CarouselSlider(props: Props): React.ReactElement {
  const { count, current, duration, onTransitionEnd } = props

  return (
    <div className="carouse-slider" onTransitionEnd={onTransitionEnd}>
      {
        Array(count).fill(0).map((item, index) => {
          // 设置单个指示器持续时长，duration = 轮播总时长 / 轮播图总数
          const style = { transitionDuration: `${duration / count}s` }
          const className = `slider-item ${index + 1 === current ? 'active' : ''}`
          return <i key={index} className={className} style={style} />
        })
      }
    </div>
  )
}

export default React.memo(CarouselSlider)

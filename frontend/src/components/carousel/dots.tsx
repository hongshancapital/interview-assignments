import React from 'react'

const CarouseDot: React.FC<{
  index: number;
  currentIndex: number;
  delay: number;
}> = (props) => {
  const isShowTime = props.currentIndex === props.index
  return <span className='carouse-dot'>
    <div className='carouse-dot-process' style={{
      width: isShowTime ? '100%' : 0,
      'transitionDuration': isShowTime ? `${props.delay}ms` : '0ms'
    }}/>
  </span>
}

const CarouselDots: React.FC<{
  showIndex: number;
  count: number;
  delay: number;
}> = ({showIndex, count, delay}) => {
  const dots = []
  for (let i = 0; i < count; i++) {
    dots.push(<CarouseDot index={i} key={i} currentIndex={showIndex} delay={delay}/>)
  }
  return <div className='carouse-dots'>{dots}</div>
}

export default CarouselDots
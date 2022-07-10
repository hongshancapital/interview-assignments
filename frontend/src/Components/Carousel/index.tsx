import React, {useEffect, useMemo, useRef, useState} from 'react';
import './index.css'




export interface CarouselItem  {
  bg: string;
  title: string;
  content: string;
  image: string;
  textColor: string,
}
interface Props {
  data: CarouselItem[];
  active?: number;
  auto?: boolean;
  duration?: number;
}

const config ={
  width: 500,
  height: 500,
  actionColor: [
    ['#ffffff', 'rgb(171,169,171)'],
    ['rgb(171,169,171)','#ffffff'],
    ['rgb(171,169,171)','#ffffff'],
  ]
}


export default  function Carousel(props: Props){
  const {data, active = 0, auto = true, duration = 2000} = props
  let [index, setIndex] = useState(active)
  let timer = useRef<ReturnType<typeof setTimeout> | null>(null)
  function move(){
    let element = document.querySelectorAll('.carousel-action-item')[index]

    let span = element.querySelector('span')
    span && span.classList.add('width-animate')
    timer.current = setTimeout(() => {
      span && span.classList.remove('width-animate')
      index ++
      if(index >=3){
        index = 0
      }
      setIndex(index)
      move()
    },duration)
  }
  const currentActionColor = config.actionColor[index]
  useEffect(() => {
    auto && move()
  }, [])

  return (
    <div className='carousel' style={{
      width: config.width,
      overflow: 'hidden'
    }}>
      <div className='carousel-content' style={{
        width: config.width * data.length,
        height: config.height,
        transform: `translate(${  -index*config.width  }px,0)`
      }}>
        {data.map((item, index) => (
          <div
            style={{
            backgroundColor: item.bg,
            width: config.width,
            height: config.height,
          }} key={index} className='carousel-content-item'>
            <div style={{
              color: item.textColor,

            }} className='carousel-content-item-title'>{item.title}</div>
            <div style={{
              color: item.textColor,
            }} className='carousel-content-item-content'>{item.content}</div>
            <img src={item.image} width={config.width}/>
          </div>
        ))}
      </div>

      <div className='carousel-action'>
        {
          data.map((item, i) => (
            <div
              data-testid={`action-item-${i}`}
              key={i}
              className={`carousel-action-item ${i === index ? 'active' : ''}`}
              onClick={e => {
                setIndex(i)
                timer.current && clearTimeout(timer.current)
              }}
            >
              <span style={{
                backgroundColor: currentActionColor[0],
                animationDuration: `${duration/1000}s`
              }}>
              </span>
              <label style={{
                backgroundColor: currentActionColor[1],
                opacity: 0.5,
              }}></label>
            </div>
          ))
        }
      </div>
    </div>
  )
}


import { url } from "inspector";
import React, { useRef, useEffect, useMemo, useCallback, useState } from "react";
import "./App.css";

import phoneUrl from './assets/iphone.png';
import tabletUrl from './assets/tablet.png';
import airpodsUrl from './assets/airpods.png';

const Carousel = ({ time = 2000, auto = true, lists = [] }: { time: number, auto?: boolean, lists: any[] }) => {
  const content = useRef<HTMLDivElement>(null)
  const timer = useRef<any>(null)
  const current = useRef<number>(0)
  const [isActive, setIsActive] = useState(0)

  const transTo = (index: number) => {
    if (content.current) {
      (content.current as HTMLDivElement).style.transform = `translateX(-${index * 100}%)`
    }
  }

  useEffect(() => {
    if (!auto) return
    if (timer.current) clearInterval(timer.current);
    timer.current = setInterval(() => {
      current.current += 1
      setIsActive(isActive + 1)
      if (current.current >= 3) {
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
  }, [auto, isActive, time])


  return <div
    className="carousel"
  >
    <div className="progress">
      {lists.map((item, index: number) => {
        return <span key={index} className={index === isActive ? 'active' : ''} onClick={() => transTo(index)}></span>
      })}

    </div>
    <div className="content" ref={content}>
      {lists.map((item, index: number) => {
        return <div className="box"
          key={index}
        >
          <div className="inner" style={{
            background: `url(${item.url}) no-repeat center/cover`,
            color: item.color
          }}>
            <div className="title">
              {item.title?.map((jtem: {} | null | undefined, jndex: number) => <p className="bold fz-50" key={jndex}>{jtem}</p>)}
              {item.desc?.map((jtem: {} | null | undefined, jndex: number) => <p className="fz-32" key={jndex}>{jtem}</p>)}
            </div>
          </div>
        </div>
      })}

    </div>

  </div>
}







function App() {

  const lists = [
    {
      title: ['xPhone'],
      desc: [
        'Lots to love.Less to spend.',
        'Starting at $399.'
      ],
      url: phoneUrl,
      color: '#fff'
    },
    {
      title: ['Tablet'],
      desc: [
        'Just the right amount of everything',
      ],
      url: tabletUrl,
      color: '#000'
    },
    {
      title: [
        'BUY a Tablet or xPhone for college.',
        'Get arPods'
      ],
      url: airpodsUrl,
      color: '#000'
    },
  ]
  return <div className="App">

    <Carousel time={3000} auto={true} lists={lists} />
  </div>;
}

export default App;

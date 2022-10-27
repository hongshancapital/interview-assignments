import React, {useEffect, useMemo, useRef, useState} from 'react';
import style from './index.module.scss'

// classnames
const cs = (...args: string[]) => args.filter(Boolean).join(' ')

export type Item = {
  title: string;
  desc?: string;
  desc2?: string;
  thumbnail?: string;
  mode?: 'light' | 'dark';
  descSize?: 'normal' | 'large';
  color?: string;
  bgColor?: string;
}

type Props = {
  items: Item[];
  duration?: number;
  className?: string;
}

type Timer = number | undefined

function Carousel({items = [], duration = 3000, className = ''}: Props) {
  const total = useMemo(() => items.length, [items])
  const initialOffset = useMemo(() => 50 * (total - 1), [total])
  const [curIdx, setCurIdx] = useState<number>(0)
  const [offsetX, setOffsetX] = useState<number>(initialOffset)
  const tm = useRef<Timer>()

  // indicator timer
  useEffect(() => {
    // @ts-ignore
    tm.current = setInterval(updateIdx, duration)

    return () => {
      clearInterval(tm.current)
    }
  }, [])

  useEffect(() => {
    if (total > 1) {
      const offset = initialOffset - 100 * curIdx
      setOffsetX(offset)
    }

    if (tm.current === undefined) {
      // @ts-ignore
      tm.current = setInterval(updateIdx, duration)
    }

  }, [curIdx, initialOffset, total])

  function clickIndicator(idx: number) {
    setCurIdx(idx)
    clearInterval(tm.current)
    tm.current = undefined
  }

  function updateIdx() {
    setCurIdx(prev => {
      if (prev < total - 1) return ++prev;
      return 0
    })
  }

  return (
    <div className={cs(style.wrap, className)}>
      <div className={style.items} style={{
        transform: `translateX(${offsetX}vw)`
      }}>
        {items.map((
          {
            title,
            desc,
            desc2,
            thumbnail,
            descSize,
            bgColor = 'white',
            color = 'black'
          }, idx) => {
          return (
            <div key={title} className={style.item} style={{
              background: bgColor,
              color
            }}>
              <div className={style.header}>
                <h2>{title}</h2>
                {desc && <p className={cs(descSize === 'large' ? 'text-large' : '')}>{desc}</p>}
                {desc2 && <p>{desc2}</p>}
              </div>
              <div className={style.thumbnail}>
                <div style={{
                  backgroundImage: `url(${thumbnail})`
                }}/>
              </div>
            </div>
          )
        })}
      </div>
      <div className={style.indicator}>
        {(Array(items.length).fill(0)).map((v, idx) => {
          const active=curIdx === idx
          return (
            <div
              key={idx}
              onClick={() => clickIndicator(idx)}
              className={active ? style.active : ''}
            >
              <span className={style.base}></span>
              <span
                className={cs(style.progress, active ? style.autoplay : '')}
                style={{
                  animation: active ? `${duration / 1000}s loading infinite` : ''
                }}
              />
            </div>
          )
        })}
      </div>
    </div>
  );
}

export default Carousel;
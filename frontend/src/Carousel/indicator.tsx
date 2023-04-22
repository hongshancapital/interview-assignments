import { useEffect } from 'react'
import style from './style.module.scss'
export interface IndicatorProps {
  total: number
  current: number
  duration: number
  handleCurrentTab: () => void
}
export default function Indicator({
  total,
  current,
  duration,
}: IndicatorProps) {
  useEffect(() => {}, [current])
  return (
    <div className={style.indicator}>
      {Array(total)
        .fill(null)
        .map((_, i) => (
          <div key={i} className={style.item}>
            <span
              className={current === i ? style.play : ''}
              style={{ animationDuration: duration / 1000 + 's' }}
            ></span>
          </div>
        ))}
    </div>
  )
}

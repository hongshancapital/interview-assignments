import {FC, ReactNode, useEffect, useState, memo, Children, useMemo} from 'react'
import styles from './index.module.css'

interface Props {
  children: ReactNode[] | ReactNode;
  duration?: number;
}

const Carousel: FC<Props> = ({children, duration}) => {
  const [activeIndex, setActiveIndex] = useState<number>(0)

  const theDuration = useMemo(() => duration || 3000, [duration])

  useEffect(() => {
    let timer: ReturnType<typeof setInterval> | null = null
    const len = Children.count(children)
    timer = setInterval(() => {
      setActiveIndex((index) => (index + 1) % len)
    }, theDuration)

    return () => {
      if (timer) {
        clearInterval(timer)
      }
    }
  }, [children, theDuration])

  return (
    <div className={styles.carousel}>
      <ul className={styles.scroller}
          style={{transform: `translate3d(-${activeIndex * 100}vw, 0, 0)`}}>
        {
          Children.map(children, (item) => (
            <li className={styles.wrapper}>{item}</li>
          ))
        }
      </ul>
      <ul className={styles.pager}>
        {
          Children.map(children, (item, index) => (
            <li className={styles.pager__item}>
              {
                index === activeIndex && (<div className={`${styles.pager__item__progress}`} style={{
                  animationDuration: `${theDuration}ms`
                }}></div>)
              }
            </li>
          ))
        }
      </ul>
    </div>
  )
}

export default memo(Carousel)

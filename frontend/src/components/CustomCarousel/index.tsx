import React, { useState, useEffect, useMemo }  from 'react'

import DotList from './DotList'

import styles from './index.module.css'

interface IProps {
  isShowDotList?: boolean;
  interview?: number;
}

export default function Carousel (props: React.PropsWithChildren<IProps>) {
  const [ index, setIndex ] = useState(-1)
  const { isShowDotList = true, interview = 3000, children } = props

  const ch = useMemo(() => {
    return children 
    ? Array.isArray(children) 
      ? children
      : [children]
    : []
  }, [children])

  const len = useMemo(() => {
    return Array.isArray(children) ? children.length : 1
  }, [children])

  useEffect(() => {
    setIndex(0)

    const timer = setInterval(() => {
      setIndex((index) => index === len - 1 ? 0 : index + 1)
    }, interview)

    return () => timer && clearInterval(timer)
  }, [])

  return (
    <div className={styles.wrapper}>
      <ul 
        className={styles.carousel}
        style={{
          transform: `translate3d(${-100 * index}%, 0, 0)`
        }}
      >
        {
          ch.map((c, i) => (
            <li data-testid="itemx" className={styles.item} key={i}>
              {c}
            </li>
          ))
        }
      </ul>

      {(isShowDotList && len > 1) && <DotList activeIndex={index} len={len} />}
    </div>
  )
}

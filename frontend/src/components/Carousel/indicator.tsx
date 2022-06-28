import React from 'react'
import { IIndicatorProps } from './interface'
import Styles from './styles.module.css'
const Indicator = (props: IIndicatorProps) => {
  const { total, currIndex } = props
  const arr = new Array(total).join(',').split(',')
  const getOuterClassName = (currIndex: number, i: number) => {
    return currIndex === i ? Styles.on : ''
  }
  const styleDot: any = {
    animationDuration: `${props.delay}ms`
  }

  return (
    <ul className={props.className}>
      {arr.map((item, i) => (
        <li
          key={i}
          className={getOuterClassName(currIndex, i)}
          onClick={() => props.onClick(i)}
          data-testid={`indicator${i}`}
        >
          {i}
          <span className={Styles.dot} style={styleDot}></span>
        </li>
      ))}
    </ul>
  )
}
export default Indicator

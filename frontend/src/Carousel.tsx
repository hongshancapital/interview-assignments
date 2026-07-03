import CSS from 'csstype';
import { JSXElementConstructor, ReactElement, useEffect, useRef, useState } from "react";
import './Carousel.css';

export default function Carousel(props: {duration?: number, style?: CSS.Properties, children: Array<ReactElement<any, string | JSXElementConstructor<any>>> }) {

  // default is 3000 ms
  const { duration = 3000 } = props

  // page count
  const itemCount = props.children.length
  // page refs
  const itemRefs = useRef<Array<HTMLDivElement | null>>([])
  // current active page
  const [currIndex, setCurrIndex] = useState(0)

  // timer to change page, run only once
  useEffect(() => {
    const intervalId = setInterval(() => {
      setCurrIndex(currIndex => (currIndex + 1) % itemCount)
    }, duration)

    return () => {
      clearInterval(intervalId)
    }
  }, [])

  // scroll page
  useEffect(() => {
    itemRefs.current[currIndex]?.scrollIntoView()
  }, [currIndex])

  return (
    <div style={{position: 'relative', ...props.style}}>
      <div className="carousel-container">
        {props.children.map((e, index) => <div className='carousel-item' key={index} ref={ref => itemRefs.current[index] = ref}>{e}</div>)}
      </div>
      <Indicator cd={duration} count={props.children.length} index={currIndex} />
    </div>
  )
}

/**
 * page indicator
 * @param props cd: time to change page in million sec, count: number of indicator items, index: current active indicator item
 * @returns
 */
function Indicator(props: {cd: number, count: number, index: number}) {

  return (
    <div className="carousel-indicator">
      {Array.from(Array(props.count).keys()).map(i => (<IndicatorItem key={i} cd={props.cd} active={props.index === i} />))}
    </div>
  )

}

/**
 * indicator item, which has a progress bar
 * @param props cd: progress duration, active: if item is active, indicate current page
 * @returns
 */
function IndicatorItem(props: {cd: number, active: boolean}) {
  const [progress, setProgress] = useState<number>(0)

  // set progress every 100ms
  useEffect(() => {
    let intervalId: number | undefined
    if (props.active) {
      intervalId = window.setInterval(() => {
        setProgress(p => p + 100)
      }, 100)
    }
    return () => {
      if (intervalId) {
        window.clearInterval(intervalId)
        setProgress(0)
      }
    }
  }, [props.active])

  return (
    <div className="carousel-indicator-item">
      <div style={{backgroundColor: 'white', height: '100%', width: `${Math.ceil(progress / props.cd * 100)}%`}}></div>
    </div>
  )
}

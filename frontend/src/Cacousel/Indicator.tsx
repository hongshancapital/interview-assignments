import type {FC} from "react"
import { useCallback, useRef, useEffect } from "react"
import './Indicator.scss'

type props = {
  count?: number
  clickDot?: (step: number)=> void
  current?: number
  delay?: number
}

const Indicator:FC<props> = (props)=> {

  const {clickDot, current, delay, count=0} = props
  const domRef = useRef<any[]>([])

  // 注册当前的dot点击事件
  const dotClick = useCallback((step: number):void=>{
    if(clickDot)clickDot(step)
  }, [clickDot])

  useEffect(()=>{
    domRef.current.map((item)=>item?.style?.setProperty('--duration', `${delay}ms`))
  }, [delay])
  
  return (
    <div className="dot-container">
      {
        new Array(count).fill(null).map((key, index)=>{
          return <div
            className={`dot-item ${current === index? 'active': ''}`} 
            onClick={(e)=>{dotClick(index)}}
            key={`dot-item-${index}`}
          >
            <div ref={(r)=>domRef.current.push(r)} className="line"/>
          </div>
        })
      }
    </div>
  )
}

export default Indicator
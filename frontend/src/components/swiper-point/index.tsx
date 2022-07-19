import React, { useEffect, useState, useRef } from "react";
import { PropsType, swiperItemType } from "../../types/index"
import "./index.css";

function SwiperPoint(props: swiperItemType) {

  const { time, isStart, index, currentIndex } = props
  const [timer, setTimer]: any = useState(0)

  const [step, setStyle] = useState(0)

  useEffect(() => {
    if (currentIndex != (index) && time != 0) {
      clearTimeout(timer)
      setStyle(0)
      setTimer(0)
      return
    }
    setStyle(50)
    let timerflag = setTimeout(() => {
      setStyle(0)
      clearTimeout(timerflag)
      setTimer(0)
    }, ((time - 1) * 1000))
    setTimer(timerflag)
  }, [time, currentIndex])

  return <div className="SwiperPoint">
    <div style={{ width: step + "px", transition: timer ? `width ${time - 1}s` : 'none' }} className="SwiperPoint-bg"></div>
  </div>
}
export default SwiperPoint

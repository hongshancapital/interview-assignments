import React, { useEffect, useState, useRef } from "react";
import { PropsType, swiperItemType } from "../../types/index"
import "./index.css";

import SwiperPoint from "../swiper-point";



function Swiper(props: PropsType) {


  const { children, isautoplay } = props;
  const interval = 4000
  const transitionInterval = interval / 1000
  const transitionIntervalBack = interval / 1000 / 4 * children.length

  const RefSwiper: any = useRef(null);
  const SwiperScreen: any = useRef(null);



  const [isSwiperItem, setIsSwiperItem] = useState(true);
  function isSwiper() {
    if (children.length) {
      let name = children[0].type.name
      setIsSwiperItem(name === "SwiperItem")
    }
  }

  const [currentIndex, setCurrentIndex] = useState(0)
  const [clientWidth, setClient] = useState(0)



  useEffect(() => {
    if (RefSwiper && isSwiperItem) {
      console.log("RefSwiper", RefSwiper)

      isSwiper()

      const { clientWidth } = RefSwiper.current
      setClient(clientWidth)

      console.log(" RefSwiper.current", RefSwiper.current.clientWidth)

    }

  }, [RefSwiper])


  useEffect(() => {
    if (clientWidth) {
      autoplay()
    }

  }, [clientWidth, isautoplay])


  let cur = 0
  function autoplay() {

    if (!isautoplay) {
      return
    }
    let time = setInterval(() => {

      console.log("cur", cur)
      console.log("children.length", children.length)
      left()
      if (cur == children.length - 1) {
        left()
        clearInterval(time)
        SwiperScreen.current.style.left = "0px"
        cur = 0
        setTimeout(() => {
          autoplay()
          setCurrentIndex(cur)
        }, interval)


        return
      }

      ++cur
      setCurrentIndex(cur)
    }, interval)
  }



  function left() {
    leftChange(1)

  }
  function right() {
    leftChange(2)
  }

  function leftChange(type: number) {
    let left = SwiperScreen.current.style.left || 0
    let leftnum = left ? left.split("p")[0] : 0;
    switch (type) {
      case 1:
        SwiperScreen.current.style.left = (leftnum - clientWidth) + "px"
        break
      case 2:
        SwiperScreen.current.style.left = (leftnum + clientWidth) + "px"
        break
    }
  }

  const swiperStyle = {
    transition: `all ${transitionInterval}s ease  `
  }
  const swiperStyleBack = {
    transition: `all ${transitionIntervalBack}s ease-in-out `
  }



  return <div ref={RefSwiper} className="swiper">{isSwiperItem ? <div ref={SwiperScreen} style={currentIndex == 0 ? swiperStyleBack : swiperStyle} className="swiper-screen" > {children} </div> : "请使用swiper"
  }
    <div className="SwiperPointBox">
      {children.map((el: any, index: number) => <SwiperPoint key={index} index={index} currentIndex={currentIndex} isStart={(index + 1) == currentIndex} time={transitionInterval} />)}
    </div>
  </div >
}

export default Swiper;

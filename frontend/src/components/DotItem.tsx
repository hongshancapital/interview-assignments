/*
 * @Author: mengtf
 * @Date: 2022-03-05 19:43:44
 * @LastEditors: mengtf
 * @LastEditTime: 2022-03-05 22:28:51
 * @Description: file content
 * @FilePath: /frontend/src/components/DotItem.tsx
 */
import React, {useState,useEffect,useCallback,useRef} from "react";
import "./Carousel.css";

type DotPorps = {
  delay: number,
  dotIndex: number,
  currentIndex: number,
  total: number,
  autoplay: boolean,
  stoped?: boolean
};

const DotItem:React.FC<DotPorps> = (props)=> {
  const {dotIndex,delay, currentIndex,autoplay, total,stoped=false} = props;
  const [style, setStyle] = useState({})
  const moveRef = useRef(null)
  let interval = useRef<NodeJS.Timeout | null>();

  const stopMove = function() {
    if (interval.current)clearInterval(interval.current)
    setStyle({visibility:"hidden"})
  }

  const move = function() {
    let elem = moveRef.current || {style:{width:""}};
    let width = 0;
    interval.current  = setInterval(frame, delay/100);
    function frame() {
      if (width >= 100) {
        if (interval.current)clearInterval(interval.current)
      } else {
        width++; 
        elem.style.width = width + '%'; 
      }
    }
  }

  useEffect(()=>{
    let currIndex = currentIndex;
    if(currIndex === total-1) currIndex = -1;
    let show = dotIndex == currIndex+1;  
    if(show){
      setStyle({visibility: (show?"visible":"hidden")})
      move();
    } else {
      setStyle({visibility: (show?"visible":"hidden"), width: 0})
    }
  },[dotIndex,currentIndex])
  
  useEffect(()=>{
    return()=>{
      if (interval.current)clearInterval(interval.current)
    }
  },[])

  useEffect(()=>{
    if(stoped)stopMove();
  },[stoped])

  return <div className="dot-item" data-index={dotIndex}>
    <div className="dot-bar" ref={moveRef} style={style}></div>
  </div>;
}
export default DotItem;

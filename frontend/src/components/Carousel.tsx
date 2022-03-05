/*
 * @Author: mengtf
 * @Date: 2022-03-05 17:25:32
 * @LastEditors: mengtf
 * @LastEditTime: 2022-03-05 22:43:57
 * @Description: file content
 * @FilePath: /frontend/src/components/Carousel.tsx
 */
import React, {useState,useEffect,useCallback,useRef} from "react";
import "./Carousel.css";
import DotItem from "./DotItem";

type SlideContent = {
    url: string,
    title?: string,
    subTitle?: string,
    contentStyle?: object
}

type CarouselOption = {
  autoplay?: boolean, // todo
  dotPosition?: string, // todo
  contentList: Array<SlideContent>,
  carouselClick?: Function,  // todo
  delay?: number // default 2000
}

const Carousel:React.FC<CarouselOption> = (props)=> {

  const {autoplay=true, dotPosition="bottom", contentList, carouselClick, delay=2000} = props;

  const [width, setWidth] = useState(0);
  const [contentStyle, setStyle] = useState({});
  const [currentIndex, setCurrentIndex] = useState(-1);
  
  let timer = useRef<NodeJS.Timeout | null>();

  const runTimer = () => {
    timer.current = setInterval(() => {
      setCurrentIndex(index=>{
        if (index<contentList.length-1) {
            return index+1
        } else {
            return 0
        }
      })
    },delay);
  }

  useEffect(()=>{
    let moveLength = 0;
    if(currentIndex<contentList.length-1){
      moveLength = (currentIndex+1)*width;
    }
    setStyle({...contentStyle,transform: `translate3d(-${moveLength}px, 0px, 0px)`,})
  }, [currentIndex])

  const slideContent = useCallback(node => {
    if (node !== null) {
      setWidth(node.getBoundingClientRect().width);
    }
  }, []);

  useEffect(() => {
    setStyle({...contentStyle, width: width*contentList.length, transition:"-webkit-transform 500ms ease-in-out 0s"})
  },[width])

  useEffect(()=>{
    if(autoplay)runTimer()
    return () => {
      if (timer.current)clearInterval(timer.current)
    }
  },[])

  const dotClick = (e: any)=>{
    if(e&&e.target){
      let elem = e.target;
      let index = parseInt(elem.dataset.index);
      if (timer.current)clearInterval(timer.current)
      setCurrentIndex(index-1);
      runTimer();
    }
  }

  return (<div className="fullContent">
    <div className="slideContent" ref={slideContent} style={contentStyle}>
      {contentList.map((slideContent:any, i) =>
        <div
            key={`${slideContent.url}_${i}`}
            className="swiperImgBox">
            <div className="swiperImg" style={{background: `url(${slideContent.url}) no-repeat center bottom / cover`}}>
              <div style={{marginBottom:"20%",...slideContent.contentStyle, maxWidth:`${width}px`}}>
                {slideContent.title&&<div className="title">{slideContent.title}</div>}
                {slideContent.subTitle&&<div className="sub-title text">{slideContent.subTitle}</div>}
              </div>
            </div>
      </div>)}
    </div>
    <div className="dotContent"  onClick={dotClick}>
      {contentList.map((slideContent:any, i) => 
      <DotItem key={`${slideContent.url}_${i}`} delay={delay} dotIndex={i} currentIndex={currentIndex} autoplay={autoplay} total={contentList.length}></DotItem>)}
    </div>
  </div>)
}

export default Carousel;

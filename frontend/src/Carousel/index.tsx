import React,{ useState, useEffect } from 'react';
import Slide from './components/Slide';
import Progress from './components/Slide/Progress'
import './index.scss'

type CarouselProps = {
    slideList: Carousel.slideItem[]
}
const Carousel = ({slideList}:CarouselProps) => {
    const [activeIndex, setActiveIndex] = useState(-1)
    const [handle, setHandle]:any = useState(null)
    
    useEffect(()=>{
        if(handle) clearTimeout(handle)
        let temp = setTimeout(()=>{
            setActiveIndex(0)
         }, 1000)
         setHandle(temp)
        return ()=> clearTimeout(handle)
    }, [])

    useEffect(()=>{
        if(handle) clearTimeout(handle)
        let temp = setTimeout(()=>{
            setActiveIndex(activeIndex+1>=slideList.length?0:activeIndex+1)
         }, 3000)
         setHandle(temp)
    }, [activeIndex, slideList]) 
    
  return (
    <div className="carousel">
      <Slide {...{slideList,activeIndex}} />
      <Progress {...{slideList,activeIndex}}/>
    </div>
  );
}

export default Carousel;

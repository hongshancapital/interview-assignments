import React,{useState, useRef, useEffect, memo, ReactElement} from "react";
import "./Carousel.scss";

type IProps = {
  children:ReactElement[]
}

const Carousel = (props:IProps) => {

  const [activeIndex, setActiveIndex] = useState(0)

  const timer = useRef<NodeJS.Timeout>()

  const carouselDom = useRef<HTMLDivElement>(null)

  useEffect(() => {
    if(props.children){
      timer.current = setInterval(() => {
        setActiveIndex(index => index === props.children.length - 1 ? 0 : index + 1)
      }, 3000)
    }

    return () => {
      timer.current && clearInterval(timer.current)
    }
  }, [props.children])

  return (<div className='carousel'  ref={carouselDom}>
    <div className='carousel-wrap' style={{transform: `translateX(-${activeIndex * (carouselDom.current?.offsetWidth || 0)}px)`}}>
      {
        props.children
      }
    </div>
    {
      !!props.children && <div className='carousel-bar'>
        {
          props.children.map((item,index) => <div key={item.key+'bar'}><div  className={`${ index === activeIndex ? 'active' : ''}`}></div></div>)
        }
      </div>
    }
  </div>)
}

export default memo(Carousel)
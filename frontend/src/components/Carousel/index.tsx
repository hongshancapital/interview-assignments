import React, {useState, useEffect} from "react";
import "./index.css";
import type { ICarouselProps } from './ICarouselProps.type';
import { ICarouselDataItemProps } from './ICarouselDataItemProps.type copy'


const Carousel: React.FC<ICarouselProps> = (props) => {
  let { dataSource } = props;
  let len = dataSource.length;

  const [actIndex, setActIndex] = useState(0);
  let [width, setWidth] = useState(1920);
  let [running, setRunning] = useState<boolean>(false);

  useEffect(() => {
    setRunning(true);
  }, [])

  
  // 初始化后，读取width
  const saveRefFn = (el: any) => {
    if (el && el.offsetWidth) {
      setWidth(el.offsetWidth)
    }
  }

  const nextIndexFn = () => {
    let newActIndex = actIndex + 1;
    if (newActIndex === len) {
        newActIndex = 0
    }
    setActIndex(newActIndex)
  }

  const saveAniRefFn = (el: any) => {
    if (el) {
      el.removeEventListener("transitionend", nextIndexFn)
      el.addEventListener("transitionend", nextIndexFn);
    }
  }

  const clickItemFn = (index: number) => {
    setActIndex(index)
  }

  return (
    <div className={`carousel ${props.className || ''}`} style={props.style} ref={el => {saveRefFn(el)}}>
      <div className="carousel-list" 
        style={{
            width: `${(len || 1) * 100}%`,
            left: `-${actIndex * width}px`
        }}>
        {dataSource.map((item: ICarouselDataItemProps) => {
            return (<div className="carousel-item" 
            style={{backgroundColor: item.backgroundColor || '#000', color: item.color || '#fff'}} 
            key={`item-${item.title}`}>
                <div className="title">{item.title}</div>
                <div className="desc">{item.desc}</div>
                <img className="img" src={item.imgUrl} />
            </div>)
        })}
      </div>
      {/* 控制条 */}
      <div className="carousel-control" style={{marginLeft: `-${54 * len / 2}px`}}>
        {dataSource.map((item: ICarouselDataItemProps, index: number) => {
            return (<div className="carousel-ctrl-box" 
            key={`ctrl-${item.title}`}
            onClick={() => {clickItemFn(index)}}>
                <div className="carousel-ctrl">
                    <div className={`ani-box ${(index === actIndex) && running ? 'active' : ''}`} ref={el => {saveAniRefFn(el)}}></div>
                </div>
            </div>)
        })}  
      </div>
    </div>
  )
}

export default Carousel;
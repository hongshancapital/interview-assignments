import React, { useEffect, useState, useRef } from 'react';
import './App.css';

type sliders = Array<{
  id: number; 
  url: string; 
  title?: string; 
  summary?: string; 
  style?: {[key: string]: string}
}>;

interface CarouselProps {
  width?: number;
  height?: number;
  hideDots?: boolean;
  autoPlay?: boolean;
  duration?: number;
  className?: string;
  sliders: sliders
}

interface dotsProps {
  tag: number;
  active: number;
  duration: number; 
  onChange: (i: number) => void;
}

function createSliders(initSlider: sliders, activeIndex: number = 0, offset: number = 800): sliders {
  return initSlider.map((item, idx) => {
    return {
      ...item,
      style: {
        ...item.style,
        position: 'absolute',
        transform: `translate3d(${(idx - activeIndex)*offset}px, 0px, 0px)`,
        WebkitTransform: `translate3d(${(idx - activeIndex)*offset}px, 0px, 0px)`,
      }
    }
  })
}

var progressAnimation = function (target: HTMLElement, durtaion: number) {
  let aniFrameID:number, 
      start:number = 0, 
      percent:number = 0, 
      step:number = 55/(60*durtaion/1000);

  target.style.animationDuration = durtaion+'ms';
  console.log(percent);
  
  const go = function () {
    aniFrameID = requestAnimationFrame(function() {
      const now = +new Date();
      if(start === 0)start = now;
      if(now - start < durtaion){
        percent += step;
        target.style.clip = `rect(0px, ${percent}px, 5px, 0px)`;
        go();
      }
    }) 

  }

  return {
    go,
    cancel: () => {
      cancelAnimationFrame(aniFrameID);
      target.style.clip = `rect(0px, 0px, 5px, 0px)`;
      start = percent = 0;
    }
  }
}

const Dots:React.FC<dotsProps> = (props) => {

  const ref = useRef(null);
  let ani = useRef(null);

  useEffect(() => {
    let target = (ref.current as unknown) as HTMLElement;
    (ani.current as unknown) = progressAnimation(target, props.duration);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    // let target = (ref.current as unknown) as HTMLElement;
    let aniRef = (ani.current as unknown) as {go: () => void, cancel: ()=>void};
    if(props.active === props.tag){
      aniRef.go()
    }else{
      aniRef.cancel();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [props.active])

  return (
    <div 
      className="dots-item" 
      onClick={() => props.onChange(props.tag)}
      style={{backgroundColor: props.active !== 0 ? 'rgba(0,0,0,0.14)' : 'rgba(255, 255, 255, 0.5)'}}
    >
      <span className="progress" ref={ref} key={props.tag}></span>
    </div>
  )
}


const settings = {
  duration: 3000
};

let activeIndex = 0, timer: any;

const Carousel:React.FC<CarouselProps> = (props) => {

  const [sliders, setSliders] = useState<sliders>(createSliders(props.sliders, activeIndex, props.width));
  const [current, setCurrent] = useState(activeIndex);

  const move = (idx: number) => {
    const _sliders = createSliders(props.sliders, idx);
    setSliders(_sliders);
  }

  const next = () => {
    if(current === sliders.length-1)return;
    var cur = current+1;
    setCurrent(cur);
    clearTimeout(timer);
    autoPlay(cur);
  }

  const prev = () => {
    if(current === 0)return;
    var cur = current-1;
    setCurrent(cur);
    clearTimeout(timer);
    autoPlay(cur);
  }

  const autoPlay = (activeIndex: number = 0) => {
    console.log(activeIndex, sliders.length);
    if(sliders.length && activeIndex === sliders.length - 1){
      activeIndex = 0;
    }else{
      activeIndex++;
    }
    timer = setTimeout(function () {
      setCurrent(activeIndex);
      autoPlay(activeIndex);
    }, props.duration || settings.duration)
  }

  useEffect(() => {

    autoPlay();

    return () => {
      clearTimeout(timer);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    move(current);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [current]);

  const handleDotsClick = (idx: number) => {
    const _sliders = createSliders(props.sliders, idx);
    setCurrent(idx);
    setSliders(_sliders);
    clearTimeout(timer);
    autoPlay(idx);
  }

  return (
    <div className="carousel">
      {
        sliders.map(item => 
          <div 
            key={item.id}
            className={`carousel-item ${props.className || ''}`}  
            style={item.style}
          >
            <div className="desc-wrapper">
              <h1>{item.title}</h1>
              <p>{item.summary}</p>
            </div>
            <div className="img-wrapper">
              <img src={item.url} alt="" />
            </div>
          </div>
        )
      }
      {
        !props.hideDots &&
        <div className="dots">
        {
          sliders.map((item, idx) => (
            <Dots key={item.id} duration={props.duration || settings.duration} tag={idx} onChange={handleDotsClick} active={current} />
          ))
        }
        </div>
      }
      <div className="prev box-content-vertical" style={{color: current !== 0 ? '#333' : '#fff'}}>
        <span onClick={prev}>上一个</span>
      </div>
      <div className="next box-content-vertical" style={{color: current !== 0 ? '#333' : '#fff'}}>
        <span onClick={next}>下一个</span>
      </div>
    </div>
  );
}

export default Carousel;

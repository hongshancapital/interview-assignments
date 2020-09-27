import React, { useState, useEffect } from "react";
import "./App.css";
const airpodsImg = require('./assets/airpods.png');
const iphoneImg = require('./assets/iphone.png');
const tabletImg = require('./assets/tablet.png');

// 主题：粗体还是正常
export enum Theme {
  bold = 'bold',
  normal = 'normal',
  light = 'light',
}
export interface SlideItem {
  title: string;
  subTitle: string;
  bgImage: string;
  textColor: string;
  theme: Theme;
}

export interface CarouselProps {
  slides: Array<SlideItem>;
  slideDuration: number;
}

const slides = [{
  title: 'xPhone',
  subTitle: 'Losts to Love.Less to Spend. Starting at $399.',
  bgImage: iphoneImg,
  textColor: '#ffffff',
  theme: Theme.normal
}, {
  title: 'Tablet',
  subTitle: 'Just the right amount of everything',
  bgImage: tabletImg,
  textColor: '#000000',
  theme: Theme.normal
}, {
  title: 'Buy a Tablet or xPhone for college.',
  subTitle: 'Get arPods.',
  bgImage: airpodsImg,
  textColor: '#000000',
  theme: Theme.bold
}];

// 进度条
interface ProgressBar {
  duration: number;
  isActive: boolean
}
const SLIDE_DURATION = 3000; // 轮播时间间隔 2000ms

function App() {
  return (<div className="App">
    <Carousel
      slides={slides}
      slideDuration={SLIDE_DURATION}
    />
  </div>);
}

// 轮播组件
function Carousel(props: CarouselProps) {
  const [currentIndex, setCurrentIndex] = useState(0);
  useEffect(() => {
    setTimeout(() => {
      setCurrentIndex(() =>
        (currentIndex + 1) % props.slides.length
      )
    }, props.slideDuration);
  });
  let wrapperStyle = {
    width: props.slides.length * 100 + '%',
    transform: `translateX(${-100 * currentIndex}vw)`
  }
  return (<div className="carousel">
    <div className="slides-wrapper" style={wrapperStyle}>
      {
        props.slides.map((item: SlideItem, index: number) => (
            <Slide
              key={index}
              title={item.title}
              subTitle={item.subTitle}
              bgImage={item.bgImage}
              textColor={item.textColor}
              theme={item.theme}
            />

        ))
      }
    </div>
    <div className='bars-wrapper'>
        {
          props.slides.map((item: SlideItem, index: number) => (
            <ProgressBars
              key={index}
              duration={SLIDE_DURATION}
              isActive={index === currentIndex}
            />
          ))
        }
      </div>
  </div>);
}

function Slide(props: SlideItem) {
  let style = {
    color: props.textColor
  }
  let textInfo = null;
  if (props.theme === Theme.normal) {
    textInfo = (
      <React.Fragment>
        <h3 className="title">{props.title}</h3>
        <h6 className="text">{props.subTitle}</h6>
      </React.Fragment>
    )
  } else if (props.theme === Theme.bold) {
    textInfo = (
      <React.Fragment>
        <h1>{props.title}</h1>
        <h1>{props.subTitle}</h1>
      </React.Fragment>
    )
  }else if (props.theme === Theme.light) { // 案例中没有细体这个模式，先和默认模式一样
    textInfo = (
      <React.Fragment>
        <h3>{props.title}</h3>
        <h1>{props.subTitle}</h1>
      </React.Fragment>
    )
  }
  return (<div className='slide-content'>
    <img className="slide-image" src={props.bgImage} />
    <div className="text-wrapper" style={style}>
      {textInfo}
    </div>
  </div>)
}


// 底部进度条
function ProgressBars(props:ProgressBar){
  let style = {};
  if(props.isActive){
    style = {
      width: '100%',
      transitionDuration: props.duration + 'ms',
      visibility: 'visible'
    }
  }else{
    style = {
      width: '0%',
      visibility: 'hidden'
    }
  }

  return (
    <div className="item-bar">
      <div className="slot"></div>
      <div className='progress-bar' style={style}></div>
    </div>
  )
}
export default App;




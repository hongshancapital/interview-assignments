import React, { CSSProperties } from "react";
import "./App.css";
import { Carousel } from "./component/Carousel/Carousel";
import { MyBullets } from "./component/MyBullets";

type SliderData = {
  lines: { type: string, content?: string, src?: string }[],
  style ? : CSSProperties
}
const sliders : SliderData[] = [
  {
    style : {
      backgroundColor: "black",
      color: "white", 
    },
    lines: [
      {type : 'h1', content : "XPhone"},
      {type : 'h2', content : "Lots to love. ,Less to spend."},
      {type : 'h2', content : "Starting at $399. "}
    ]
  },
  {
    lines: [
      {
        type: 'h1',
        content: "Tablet"
      }, {
        type: 'h2',
        content: "just the right amount of everything"
      }]
  },
  {
    lines : [
      {
        type : 'h1',
        content : 'Buy a Tablet or XPhone for college.'
      },
      {
        type : 'h1',
        content : 'Get arPods'
      }
    ]
  }
]

const Slider = ( props : SliderData & {className? : string} ) => {
  return <div style={props.style} className={props.className}>
    {props.lines.map( ({type, content, src}, i) => {
      switch(type) {
        case 'h1':
          return <h1 key={i}>{content}</h1>
        case 'h2':
          return <h2 key={i}>{content}</h2>
        case 'img':
          return <img alt="" src={src} key={i} />
        default :
          throw new Error(`type ${type} not supported.`)
      }
    })}
  </div>
}





function App() {
  return (
    <div className="App">
      <Info>
        （与视频相同
        ）：滑到最右边，然后滑动到最左边
      </Info>
      <Carousel
        style={{
          height: "190px",
        }}
        duration={1500}
        wait={2000}
        dir="right"
        mode="one-side"
        addons = {<MyBullets />}
      >
        {sliders.map((slider, i) => {
          return <Slider {...slider} key={i} />
        })}
      </Carousel>

      <Info>
        （与视频相同方向相反
        ）：滑到最右边，然后滑动到最左边
      </Info>
      <Carousel
        style={{
          height: "190px",
        }}
        duration={800}
        wait={2500}
        dir="left"
        mode="one-side"
        addons = {<MyBullets />}
      >
        {sliders.map((slider, i) => {
          return <Slider {...slider} key={i} />
        })}
      </Carousel>

      <Info>单向无限循环（的轮播图）</Info>
      <Carousel
        mode="loop"
        duration={300}
        wait={1000}
        style={{
          height: "190px",
        }}
        addons = {<MyBullets />}
      >
        {sliders.map((slider, i) => {
          return <Slider {...slider} key={i} />
        })}
      </Carousel> 


      <Info>单向无限循环反方向（的轮播图）</Info>
      <Carousel
        mode="loop"
        duration={1000}
        wait={1000}
        style={{
          height: "190px",
        }}
        dir="right"
        addons = {<MyBullets />}
      >
        {sliders.map((slider, i) => {
          return <Slider {...slider} key={i} />
        })}
      </Carousel> 
    </div>
  )
}
const Info = ({children} : {children : string}) => {
  return <h3 style={{
    color : 'blue'
  }}>{children}</h3>
}


export default App;

import './App.css';
import Carousel from "@/components/Carousel/index"
import ConsolePanel from "@/components/Carousel/consolePanel"
import React, { useState } from 'react';
import iphone from "@/assets/iphone.png"
import ipad from "@/assets/ipad.png"
import airpods from "@/assets/airpods.png"
import pick from "@/utils/pick"
import type { CarouselOnChange } from "@/components/Carousel"
type CarouselItemViewProps={
  title?:string|React.ReactNode,
  description?:string|React.ReactNode,
  src?:string,
}
const CarouselItemView: ReactFCStyle<CarouselItemViewProps>=(props)=>{
  const across = pick(['style','className'])(props)
  return <div style={{ background: "#222", color: "#fff", ...across.style }} className={'carousel-item ' + (props.className||'')}>
    <h1>{props.title}</h1>
    <p> {props.description}  </p>
    <img src={props.src} alt="carousel-img" style={{ width: '5rem', marginTop: "10rem" }} />
  </div>
}

function App() {
  const [currentIndex, currentIndexSet] = useState(2)
  const [autoplay, autoplaySet] = useState(true)
  const [duration, durationSet] = useState(2000)
  const onCarouselChange: CarouselOnChange = (index) => {
    currentIndexSet(index)
    return
  }
  return <div className='App'>
    <Carousel 
        value={currentIndex} 
        onChange={onCarouselChange} 
        autoplay={autoplay} 
        disableDots={false} 
        duration={duration}
      >
      <CarouselItemView
        style={{ background: "#111", color: "#fff" }}
       title="xPhone"
        description={<span>Lots to love. Less to spend.<br />Starting at $399.  </span>}
        src={iphone}
      ></CarouselItemView>

      <CarouselItemView
        style={{ background: "#fafafa", color: "#000" }}
        title="Tablet"
        description="Just the right amount of everything."
        src={ipad}
      ></CarouselItemView>
      
      <CarouselItemView
        style={{ background: "#fafafa", color: "#000" }}
        title="Buy a Tablet or xPhone for college. Get arPods"
        src={airpods}
      ></CarouselItemView>

    </Carousel>

    <ConsolePanel
      currentIndex={currentIndex}
      autoplay={autoplay}
      autoplaySet={autoplaySet}
      duration={duration}
      durationSet={durationSet}
    ></ConsolePanel>
  </div>;
}

export default App;

import React, { FunctionComponent, createRef } from "react";
import Carousel, { CarouselItem } from './components/Carousel'
import { TCarouselProps, TCarouselFun } from './components/Carousel/types'
import "./App.scss";

const App: FunctionComponent = () => {
  const $Carousel = createRef<TCarouselFun>()
  const list = new Array(7).fill(0)
  const carouselProps: TCarouselProps = {
    autoPlay: true,
    onTransitionStart: (startIndex, endIndex) => {
      console.log(`transition start => startIndex ${startIndex} endIndex ${endIndex}`)
    },
    onTransitionEnd: (startIndex, endIndex) => {
      console.log(`transition complete => startIndex ${startIndex} endIndex ${endIndex}`)
      console.log('---------------------------------')
    }
  }
  const onJump = (index: number) => {
    $Carousel.current?.jump(index)
  }
  const onStart = () => {
    $Carousel.current?.start()
  }
  const onParse = () => {
    $Carousel.current?.parse()
  }
  return <div className="App">
    <Carousel {...carouselProps} ref={$Carousel}>
      {list.map((i, index) => <CarouselItem key={i}>
        <div className="slide-item">{`CarouselItem ${index}`}</div>
      </CarouselItem>)}
    </Carousel>
    <div className="action action1">
      {list.map((d, index) => <div onClick={onJump.bind(null, index)} key={index}>{index}</div>)}
    </div>
    <div className="action action2">
      <div onClick={onStart}>开始</div>
      <div onClick={onParse}>暂停</div>
    </div>
  </div>;
}

export default App;

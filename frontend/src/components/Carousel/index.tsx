import React from "react";
import './index.css'
export interface ICarouselProps{
  // loop?: boolean,
  // delay?:number,
  // autoPlay?: boolean,
  // imageList?:string[]
}
export const Carousel=(props:ICarouselProps)=>{
    // const {
    //   loop,
    //   delay,
    //   autoPlay,
    //   imageList
    // }=props
  return <div className="carousel-wrapper">
    <div className="carousel-content">
    <p>this is a test</p>

    </div>
  </div>

}

export default Carousel
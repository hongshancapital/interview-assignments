import React from "react";
// import { ClickProps } from './slider';
import './index.css';
interface ArrowProps {
 clickHandler: Function
}
export const PrevArrow = (props: ArrowProps) => {
  return (
    <div
      className="syq-arrows syq-arrows-prev"
      onClick={(e: any) => {
        props.clickHandler({
          type: 'prevArrow'
        })
      }}/>
  )

}



export const NextArrow = (props: ArrowProps) => {
   return (
    <div 
      className="syq-arrows syq-arrows-next"
      onClick={() => {
      props.clickHandler({
        type: 'nextArrow'
      })
    }}/>
  )

}
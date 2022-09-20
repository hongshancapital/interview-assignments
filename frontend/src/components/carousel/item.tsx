import React from 'react'
import { CarouseContext } from '.'
import './styles/index.css'

export interface CarouseItemType extends React.FC<{
  className?: string;
}> {

}
const Item: React.FC<{
  className?: string;
  delay: number;
  showIndex: number;
  duration: number;
  animateType?: string;
}> = (props) => {
  return <div className={`carouse-item ${props.className || ''}`} style={{
    left: `-${props.showIndex * 100}%`,
    transitionDuration: `${props.duration}ms`,
    transitionTimingFunction: props.animateType
  }}>
  {props.children}
</div>
}
const CarouselItem: CarouseItemType = (props) => {
  return <CarouseContext.Consumer>
    {
      ctx => {
        return <Item 
          className={props.className} 
          showIndex={ctx.showIndex} 
          delay={ctx.delay}
          duration={ctx.duration}
          animateType={ctx.animateType}>{props.children}</Item>
      }
    }
  </CarouseContext.Consumer>
}
export default CarouselItem
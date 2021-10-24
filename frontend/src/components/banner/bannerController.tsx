import React, { FC, ReactElement} from 'react'
import {IBannerImage, IProps} from "./typing/banner"

const BannerController:FC<IProps> = (props):ReactElement => {
  const innerStyle = {
    transition: `width ${props.stayTime}s linear`
  }


  const controllerList = props.data.map( (item:IBannerImage, index:number)=>{
    if(index === props.current){
      return (
        <li className="ct-btn" key={item.des as string}>
          <div style={innerStyle} className="ct-btn-inner active"> </div>
        </li>
      )
    } else {
      return (
        <li className="ct-btn" key={item.des as string}>
          <div style={innerStyle}  className="ct-btn-inner"> </div>
        </li>
      )
    }
  })
  return (
    <div className="controller">
      <ul>{controllerList}</ul>
    </div>
  )
}

export default BannerController
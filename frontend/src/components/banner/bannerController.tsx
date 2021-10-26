import React, { FC, ReactElement} from 'react'
import {IBannerImage, IProps} from "./typing/banner"

const BannerController:FC<IProps> = (props):ReactElement => {
  const innerStyle = {
    transition: `width ${props.stayTime}s linear`
  }


  const controllerList = props.data.map( (item:IBannerImage, index:number)=>{
    const btnClassName = index === props.current ? 'ct-btn-inner active' : 'ct-btn-inner'
    return (
      <li className="ct-btn" key={item.des as string}>
        <div style={innerStyle} className={btnClassName}> </div>
      </li>
    )
  })
  return (
    <div className="controller">
      <ul>{controllerList}</ul>
    </div>
  )
}

export default BannerController
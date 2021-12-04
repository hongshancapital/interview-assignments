import React from "react"
import './index.css'
export interface IndecatorProps {
  length: number,
  delay:number,
  index:number,

}
export const Indecator=(props:IndecatorProps)=>{
  const itemList=Array.from({length:props.length}).fill(0)
  return <div className="indecator-wrapper">
    {itemList.map((_,index)=>(<div key={index} className={props.index===index?'item current':'item'}  ></div>))}
  </div>
}

export default Indecator
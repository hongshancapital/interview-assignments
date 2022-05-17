import React from "react";
import { ItemProps } from '../../interface'
import {imgTypeList, themeMap } from './const';
import './index.scss'

const prefix = 'item';
/**
 * item组件
 */
const Item:React.FC<ItemProps> = (props)=>{
  const { imgType, theme, subTitle , title } = props;
  return (
    <div
      className={`${prefix}-wrap`}
      style={{ color: themeMap[theme].fontColor}}
    >
      <div className={`${prefix}-introduct`} >
        {title.map(context => <div className={`${prefix}-title`} key={context}>{context}</div>)}
        {subTitle?.map(subContext => <div className={`${prefix}-subTitle`} key={subContext}>{subContext}</div>)}
      </div>
      <div
        className={`${prefix}-bg`}
        style={{ backgroundColor:themeMap[theme].bgColor}}>
        <img
          src={imgTypeList[imgType]}
          alt={''}
          className={`${prefix}-img`}
        />
      </div>
    </div>
  )
}

export default Item;
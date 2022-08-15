import React from "react";
import './index.css'
import { IProduct } from '../../interface'
const prefix = 'product';

const matchImg = {
  iphone: require('../../assets/iphone.png'),
  airpods: require('../../assets/airpods.png'),
  tablet: require('../../assets/tablet.png')
}
/**
 * 商品组件
 */
function Product(props: IProduct) {
  const { imgName, theme } = props;
  return (
    <div
      className={`${prefix}__container`}
      style={{ color: theme.fontColor }}
    >
      <div
        className={`${prefix}__bg`}
        style={{ backgroundColor: theme.bgColor }}>
        <img
          src={matchImg[imgName]}
          alt={imgName}
          className={`${prefix}__img`}
        />
      </div>
      <div className={`${prefix}__introduct`} >
        {props.title.map(text => <div className={`${prefix}__title`} key={text}>{text}</div>)}
        {props.descList?.map(text => <div className={`${prefix}__desc`} key={text}>{text}</div>)}
      </div>
    </div>
  )
}

export default Product
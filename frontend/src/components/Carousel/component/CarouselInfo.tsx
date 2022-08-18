import React, { FC, ReactNode } from "react";
import styles from '../carousel.module.css'

interface IProps {
  title: string[]|string
  desc: string[]|string
  image: string,
  fontColor: string
}
/**
 * 
 * @param title 标题
 * @param desc 描述
 * @param image 图片地址
 * @param fontColor 字体颜色
 * @returns 
 */
export const CarouselInfo:FC<IProps> =({
  title=[],
  desc=[],
  image='',
  fontColor='#fff'
}) => {

  const renderTitle = ():ReactNode => {
    const isArr = Array.isArray(title) 
    return (
      <>
        {
          isArr ? title.map((item) => (
            <h1 key={item}>{item}</h1>
          )): <h1>{title}</h1>
        }
      </>
    )
  }

  const renderDesc = ():ReactNode => {
    const isArr = Array.isArray(desc) 
    return (
      <>
        {
          isArr ? desc.map((item) => (
            <p key={item}>{item}</p>
          )): <p>{desc}</p>
        }
      </>
    )
  }

  return (
    <div className={styles.carousel_info_container} 
    style={{
      width: '100%',
      height: '100%'
    }}
    >
      <div className={styles.carousel_info_info} style={{color:fontColor}}>
        {renderTitle()}
        {renderDesc()}
      </div>
      <div className={styles.carousel_info_image_container}>
        <img src={image} alt="sanmu" className={styles.carousel_info_image}/>
      </div>
    </div>
  )
}
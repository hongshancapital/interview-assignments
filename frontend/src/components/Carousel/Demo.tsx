import React from 'react'
import styles from './index.module.css'

export interface RenderData {
  id: number,
  // 背景色
  backgroundColor: string,
  // 文字色
  textColor: string,
  // 背景图
  backgroundImage: string,
  // 标题
  title: string,
  // 子标题
  subTitle?: string
}

export default function Demo(props: RenderData) {
  return (
    <div
      className={styles.demo}
      style={{ backgroundColor: props.backgroundColor }}
    >
      <div className={styles.ads} style={{ color: props.textColor }}>
        <h1 className={styles.title}>{props.title}</h1>
        {props.subTitle && (<p className={styles.subtitle}>{props.subTitle}</p>)}
      </div>
      <img className={styles.bg} src={props.backgroundImage} alt={props.title} />
    </div>
  )
}
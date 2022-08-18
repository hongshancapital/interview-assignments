import React, { FC, ReactNode, useEffect, useState } from "react"
import styles from "./carousel.module.css"

interface IProps {
  children: ReactNode
  switchingTime: number
}

export const Carousel:FC<IProps> = ({
  children = React.createElement('div'),
  switchingTime = 3
}) => {
  const [activeIndex, setActiveIndex] = useState<number>(0)

  /**
   * 重置动画
   */
  const replayAnimations = () => {
    document.getAnimations().forEach((anim:Animation) => {
      anim.cancel();
      anim.play()
    })
  }

  /**
   * 更新索引
   * @param index 索引
   */
  const changeIndex = (index:number) => {
    if(index < 0) {
      index = React.Children.count(children) - 1;
    }else if(index >= React.Children.count(children) ) {
      index = 0;
    }

    setActiveIndex(index)
    replayAnimations()
  }

  /**
   * 底部加载条点击事件
   * @param index 跳转索引值
   */
  const onClickCarouselIndex = (index:number) => {
    changeIndex(index);
    replayAnimations()
  }

  useEffect(() => {
    const interval = setInterval(() => {
      changeIndex(activeIndex+1)
    },switchingTime*1000)

    return () => {
      if(interval) clearInterval(interval)
    }
  })


  return (
    <div className={styles.container}>
      <div
        className={styles.inner}
        style={{
          transform:`translateX(-${activeIndex * 100}%)`
        }}
      >
        {
          React.Children.map(children, (child:any) => {
            return React.cloneElement(child, {width: "100%", height: "100vh"})
          })
        }
      </div>
      <div className={styles.loading}>
        {
          React.Children.map(children, (child,index) => {
            return (
              <div 
              className={styles.indicator_outer}
              onClick={() => onClickCarouselIndex(index)}
              >
                <div
                className={styles.indicator_inside}
                style={{
                  animationDuration: index === activeIndex ? `${switchingTime}s` : "0s",
                  backgroundColor: index === activeIndex ? '#FFF':''
                }}
                ></div>
              </div>
            )
          })
        }
      </div>
    </div>
  )
}
import { FC, ReactNode, useCallback, useMemo, useState } from 'react'
import './index.css'

export interface ICarouselProps {
  duration?: number;
  children: ReactNode[];
}

const bannerDuration = 300;

/**
 * components下为纯交互组件，与业务数据无关
 */
const Carousel: FC<ICarouselProps> = (props) => {
  const { duration = 3000, children = [] } = props
  const [activeIndex, setActiveIndex] = useState<number>(0)
  const [moduleDuration, setModuleDuration] = useState<number>(bannerDuration / 1000)

  const tabAnimationClass = useMemo(() => ({
    animationName: "carousel-tab-animation",
    animationDuration: `${duration / 1000}s`,
  }), [duration])

  const bannerTransitionEnd = useCallback(() => {
    if (activeIndex === children.length) {
      /**
       * 实现最后一张继续向右轮播的辅助方法
       * 最后一张轮播完成后，关闭运动效果，将第carouselData.length+1张的位置替换为第1张的位置
       */
      setModuleDuration(0);
      setActiveIndex(0)
      setTimeout(() => setModuleDuration(moduleDuration), bannerDuration)
    }
  }, [activeIndex, children, moduleDuration])

  return (
    <div className="carousel-wrapper">
      <div
        className="carousel-list"
        data-testid="carousel-list"
        style={{
          transform: `translateX(-${activeIndex}00%)`,
          transitionDuration: `${moduleDuration}s`
        }}
        onTransitionEnd={bannerTransitionEnd}>
        {children}
        {
          /**
           * 实现最后一张继续向右轮播的效果
           * 在「最后一张」右边拼接「第一张」
           */
        }
        {children?.length > 1 && children?.[0]}
      </div>

      <div className="carousel-tab-list" data-testid="carousel-tab-list">
        {
          children?.length > 1 && children.map((item, index) => {
            /**
             * 实现最后一张继续向右轮播的辅助方法
             * 当前在第carouselData.length + 1张时，tab第一个按钮为激活状态
            */
            const actived = index === activeIndex || (activeIndex === children.length && index === 0);

            return <div
              className="carousel-tab-item"
              data-testid="carousel-tab-item"
              key={index}
              onClick={() => setActiveIndex(index)}>
              <div
                className="carousel-tab-inner"
                style={actived ? tabAnimationClass : {}}
                onAnimationEnd={() => setActiveIndex(activeIndex + 1)}
              ></div>
              <div className="carousel-tab-hotspot"></div>
            </div>
          })
        }
      </div>
    </div >
  )
}

export default Carousel

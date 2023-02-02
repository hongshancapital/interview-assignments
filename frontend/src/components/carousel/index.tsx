import { FC, ReactNode, useCallback, useMemo, useState } from 'react'
import './index.css'

export interface ICarouselProps {
  duration?: number; // 每页停留的时长
  children?: ReactNode[]; // 每页显示的模块
  carouselIds?: number[]; // 渲染indicator列表时添加key
}

const BANNER_REDUTION = 300; // banner每次运动的时长

/**
 * 轮播图交互特效组件，不涉及业务数据
 * @param props ICarouselProps
 */
const Carousel: FC<ICarouselProps> = (props) => {
  const { duration = 3000, children: carouselList = [], carouselIds = [] } = props
  const [activeIndex, setActiveIndex] = useState<number>(0)
  const [moduleDuration, setModuleDuration] = useState<number>(BANNER_REDUTION / 1000)

  // banner 的动画样式
  const bannerAnimationClass = useMemo(() => ({
    transform: `translateX(-${activeIndex}00%)`,
    transitionDuration: `${moduleDuration}s`
  }), [activeIndex, moduleDuration])

  // tab 的动画样式
  const tabAnimationClass = useMemo(() => ({
    animationName: "carousel-tab-animation",
    animationDuration: `${duration / 1000}s`,
  }), [duration])

  const bannerTransitionEnd = useCallback(() => {
    if (activeIndex === carouselList.length) {
      /**
       * 实现最后一张继续向右轮播的辅助方法
       * 最后一张轮播完成后，关闭运动效果，将第 carouselData.length +1张的位置替换为第1张的位置
       */
      setModuleDuration(0);
      setActiveIndex(0)
      setTimeout(() => setModuleDuration(moduleDuration), BANNER_REDUTION)
    }
  }, [activeIndex, carouselList, moduleDuration])

  return (
    <div className="carousel-wrapper" data-testid="carousel-wrapper">
      <div
        className="carousel-list"
        data-testid="carousel-list"
        style={bannerAnimationClass}
        onTransitionEnd={bannerTransitionEnd}>
        {carouselList}
        {
          /**
           * 实现最后一张继续向右轮播的效果
           * 在「最后一张」右边拼接「第一张」
           */
        }
        {carouselList?.length > 1 && carouselList[0]}
      </div>

      <div className="carousel-tab-list" data-testid="carousel-tab-list">
        {
          carouselIds?.length > 1 && carouselIds.map((id, index) => {
            /**
             * 实现最后一张继续向右轮播的辅助方法
             * 当前在第 carouselData.length + 1张时，tab第一个按钮为激活状态
             */
            const splicedActive = activeIndex === carouselIds.length && index === 0;
            // indicator是否为选中
            const actived = index === activeIndex || splicedActive;
            const activeClass = actived ? tabAnimationClass : {}

            return <div
              className="carousel-tab-item"
              data-testid="carousel-tab-item"
              key={id}
              onClick={() => setActiveIndex(index)}>
              <div
                className="carousel-tab-inner"
                style={activeClass}
                // indicator动画结束后切换到下一页
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

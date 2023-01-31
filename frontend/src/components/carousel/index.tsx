import React, { FC, ReactNode, useCallback, useMemo, useState } from 'react'
// import classnames from 'classnames'
import './index.css'
import { MockData } from '../../mock/data'
import { ICarouselItem } from '../../types/carousel'

interface IProps {
  duration?: number
  children?: Array<ReactNode>;
}

const bannerDuration = 300;

/**
 * components下为纯交互组件，与业务数据无关
 */
const Carousel: FC<IProps> = (props) => {
  const { duration = 3000 } = props
  const [carouselData] = useState<ICarouselItem[]>(MockData)
  const [activeIndex, setActiveIndex] = useState<number>(0)
  const [moduleDuration, setModuleDuration] = useState<number>(bannerDuration / 1000)

  const tabAnimationClass = {
    animationName: "carousel-tab-animation",
    animationDuration: `${duration / 1000}s`,
  };

  const changePage = useCallback((nextPage: number) => {
    setActiveIndex(nextPage)
  }, [carouselData])

  const bannerTransitionEnd = useCallback(() => {
    if (activeIndex === carouselData.length) {
      /**
       * 实现最后一张继续向右轮播的辅助方法
       * 最后一张轮播完成后，关闭运动效果，将第carouselData.length+1张的位置替换为第1张的位置
       */
      setModuleDuration(0);
      setActiveIndex(0)
      setTimeout(() => setModuleDuration(moduleDuration), bannerDuration)
    }
  }, [activeIndex, carouselData])

  const renderData = useMemo(() => {
    /**
     * 在最后一个banner右边拼接第一个banner，实现自动轮播时无限向右轮播效果，
     * 避免最后一张向第一张运动时(you)，跨越中间多张图片，影响用户体验
     * 深拷贝在生产环境推荐使用「常规深拷贝函数」，不推荐使用JSON.parse(JSON.stringify(obj))
     */
    const copyItem = JSON.parse(JSON.stringify(carouselData[0]));
    copyItem.id = -1;

    return carouselData.concat(copyItem);
  }, [carouselData])

  return (
    <div className="carousel-wrapper">
      <div
        className="carousel-list"
        style={{
          transform: `translateX(-${activeIndex}00%)`,
          transitionDuration: `${moduleDuration}s`
        }}
        onTransitionEnd={bannerTransitionEnd}>
        {
          renderData.map(item => (
            <div
              className="carousel-item"
              key={item.id}
              style={{ backgroundColor: item.bgColor, color: item.fontColor }}>
              <div className="carousel-item-info">
                <div className="carousel-item-title">
                  {item.title}
                </div>
                {
                  item.desc && <div className="carousel-item-desc">
                    {item.desc.map(text => <p>${text}</p>)}
                  </div>
                }
              </div>
              <div
                className="carousel-item-img"
                style={{ backgroundImage: `url(${item.bgImg})` }}
              ></div>
            </div>
          ))
        }
      </div>

      <div className="carousel-tab-list">
        {
          carouselData.map((item, index) => {
            /**
             * 实现最后一张继续向右轮播的辅助方法
             * 当前在第carouselData.length + 1张时，tab第一个按钮为激活状态
            */
            const actived = index === activeIndex || (activeIndex === carouselData.length && index === 0);

            return <div className="carousel-tab-item" key={item.id} onClick={() => changePage(index)}>
              <div
                className="carousel-tab-inner"
                style={actived ? tabAnimationClass : {}}
                onAnimationEnd={() => changePage(activeIndex + 1)}
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

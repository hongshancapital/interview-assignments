/**
 * @description 核心轮播逻辑，可基于此创建多种样式的轮播，放置在carousel/type中 
 */
import { useState, useMemo, useCallback, ReactNode } from 'react'
import './index.css'

interface CorePropsFace {
    duration?: number; // 停留时长
    children: ReactNode[]; // 轮播块
}
const MOVE_TIMES = 300; // 移动时长

export default function Carousel(props: CorePropsFace) {
    const { duration = 3000, children: childNodeList = [] } = props
    const [activeIndex, setActiveIndex] = useState<number>(0)
    const [bannerDuration, setBannerDuration] = useState<number>(MOVE_TIMES / 1000)
    // banner 样式
    const bannerAnimaStyle= useMemo(() => ({
        transform: `translateX(-${activeIndex}00%)`,
        transitionDuration: `${bannerDuration}s`
    }), [activeIndex, bannerDuration])
    // tab 样式
    const tabAnimaStyle = useMemo(() => ({
        animationName: "carousel-tab-grow-animation",
        animationDuration: `${duration / 1000}s`,
    }), [duration])
    // 每次轮播结束
    const bannerTransEnd = useCallback(() => {
        if (activeIndex === childNodeList.length) {
            // 恢复起始轮播位置
            setActiveIndex(0)
            setBannerDuration(0);
            setTimeout(() => setBannerDuration(bannerDuration), MOVE_TIMES)
        }
    }, [activeIndex, bannerDuration, childNodeList])

    return (
        <div className="carousel-container">
            {/* 轮播块 */}
            <div style={bannerAnimaStyle} className="carousel-banner-box" onTransitionEnd={bannerTransEnd}>
                { childNodeList }
                {/* 末尾拼接第一张banner，实现最后一张和第一张无缝衔接 */}
                { childNodeList?.length > 1 && childNodeList[0] }
            </div>
            {/* 轮播 tab */}
            <div className="carousel-tab-box">
                {
                    childNodeList?.length > 1 && childNodeList.map((el, index) => {
                        const isLastBanner = activeIndex === childNodeList.length && index === 0;
                        const isActive = index === activeIndex || isLastBanner;
                        const activeClass = isActive ? tabAnimaStyle : {}

                        return (
                            <div
                                key={index}
                                className="carousel-tab-item"
                                onClick={() => setActiveIndex(index)}>
                                <div
                                    style={activeClass}
                                    className="carousel-tab-grow"
                                    onAnimationEnd={() => setActiveIndex(activeIndex + 1)}
                                ></div>
                            </div>
                        )
                    })
                }
            </div>
        </div>
    )
}
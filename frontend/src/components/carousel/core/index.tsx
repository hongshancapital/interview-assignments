/**
 * @description 核心轮播逻辑，可基于此创建多种样式的轮播，放置在carousel/type中 
 */
import { useState, useMemo, ReactNode } from 'react'
import './index.css'
// 轮播计时器
let intervalControl: any = null

interface CorePropsFace {
    duration?: number; // 停留时长
    children: ReactNode[]; // 轮播块
    isShowTab?: boolean; // 是否展示tab
}
const MOVE_TIMES = 300; // 移动时长

export default function Carousel(props: CorePropsFace) {
    const { duration = 3000, children: childNodeList = [], isShowTab = true } = props
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
    // 轮播控制
    intervalControl && clearInterval(intervalControl)
    intervalControl = setInterval(() => {
        if(activeIndex === childNodeList.length - 1) {
            // 恢复初始位置，无缝衔接
            setTimeout(() => {
                setActiveIndex(0)
                setBannerDuration(0)
                // 恢复移动时长
                setTimeout(() => setBannerDuration(bannerDuration), MOVE_TIMES)
            }, MOVE_TIMES)
        }
        setActiveIndex(activeIndex + 1)
    }, duration)

    return (
        <div className="carousel-container">
            {/* 轮播块 */}
            <div style={bannerAnimaStyle} className="carousel-banner-box">
                { childNodeList }
                {/* 末尾拼接第一张banner，实现最后一张和第一张无缝衔接 */}
                { childNodeList?.length > 1 && childNodeList[0] }
            </div>
            {/* 轮播 tab */}
            {
                isShowTab && <div className="carousel-tab-box">
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
                                ></div>
                            </div>
                        )
                    })
                }
            </div>
            }
        </div>
    )
}
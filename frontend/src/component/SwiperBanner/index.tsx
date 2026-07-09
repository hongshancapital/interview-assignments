/*
 * @Description: 红杉 面试
 * @Date: 2023-04-17 19:28:32
 * @LastEditors: Yaco && 970864912@qq.com
 * @LastEditTime: 2023-04-17 21:55:14
 */

import './index.css'

import React, { useState, useEffect } from 'react'


export function SwiperBanner() {

    // 外层容器宽度 
    const [containerWidth, setContainerWidth] = useState(0)

    // 图片数组
    const imgArr: Array<String> = ['1', '2', '3']

    // banner 索引
    const [bannerIdx, setBannerIdx] = useState(0)

    // 定时器
    let timer: any

    useEffect(() => {

        //  避免重复计算
        if (!containerWidth) {
            let ctxDom: any = document.querySelector('.banner-container');
            if (ctxDom) setContainerWidth(ctxDom.offsetWidth || 0)
        }

        // 开启动画
        startAnimate()

        // 销毁
        return () => {
            endAnimate()
        }

    })

    // 开始动画
    function startAnimate() {
        timer = setInterval(() => {
            changeIndex('next')
        }, 2000)
    }

    // 停止动画
    function endAnimate() {
        if (timer) clearInterval(timer)
    }

    /**
     * @description:修改 banner 索引
     * @param {*} type
     * @return {*}
     */
    function changeIndex(type: 'pre' | 'next'): void {
        let len = imgArr.length
        if (type === 'next') bannerIdx < len - 1 ? setBannerIdx(bannerIdx + 1) : setBannerIdx(0)
        if (type === 'pre') bannerIdx <= 0 ? setBannerIdx(len - 1) : setBannerIdx(bannerIdx - 1)
    }



    return <>
        <div className="banner-container">

            {/* 走马灯容器 */}
            <div className='swiper-content' style={{ 'left': - containerWidth * bannerIdx }} >
                {imgArr.map(item => {
                    return <div className='swiper-item' key={item.toString()}>
                        IMG_{item}
                    </div>
                })}
            </div>

            {/* 导航按钮 */}
            <input className='btn btn-pre' type="button" value="<" onClick={() => changeIndex('pre')} />
            <input className='btn btn-next' type="button" value=">" onClick={() => changeIndex('next')} />

            {/* 导航进度 */}
            <div className='swiper-navigation'>
                <div className='navgation-bar'>
                    {imgArr.map((item, index) => {
                        return <div key={index} className={index === bannerIdx ? 'bar-item active' : 'bar-item'}>
                            <span className='slide-footer'></span>
                        </div>
                    })}
                </div>
            </div>
        </div>
    </>
}
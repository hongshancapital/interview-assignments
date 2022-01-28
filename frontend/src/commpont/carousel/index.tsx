import React, { useEffect } from "react";
import Swiper from "swiper"
import "swiper/css/swiper.css"
import './index.less'

function Carousel() {
    
    useEffect(() => {
        new Swiper('.swiper-container', {
            direction: 'horizontal', // 水平切换选项
            loop: true, // 循环模式选项
            autoplay:true,
            // 如果需要分页器
            pagination: {
                el: '.swiper-pagination',
            },
        })
    }, []);

    /**swiper需要加载的数据 */
    const swiperList = [
        {
            title: "Buy a Table or xPhone for college.",
            desc: "Get arPods",
            backImage:'demo-swiper-img-one'
        },
        {
            title: "xPhone",
            desc: "Lots to love, Less to spand",
            price: "Starting at $399",
            backImage:'demo-swiper-img-two'
        },
        {
            title: "Tablet",
            desc: "Just the right amount of everything.",
            backImage:'demo-swiper-img-three'
        }
    ]

    return (
        <div className="swiper-container demo-swiper-container">
            <div className="swiper-wrapper">
                {
                    swiperList && swiperList.map((item, index) => (
                        <div key={index} className={`swiper-slide ${item?.backImage}`}>
                            <h1 className="demo-swiper-title">{item?.title}</h1>
                            <p className="demo-swiper-desc">{item?.desc}</p>
                            <p className="demo-swiper-desc">{item?.price}</p>
                        </div>
                    ))
                }
            </div>
            {/* <!-- 如果需要分页器 --> */}
            <div className="swiper-pagination"></div>
        </div>
    )
}
export default Carousel;
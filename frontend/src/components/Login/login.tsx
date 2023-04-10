import React from 'react'
import { Swiper, SwiperSlide } from "swiper/react";
import './Login.css'
import SwiperCore, { Navigation, Pagination, Scrollbar, Autoplay, A11y } from 'swiper';
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";

export default function Banner() {
  return (
    <div className="Banner">
      <Swiper
        initialSlide={0}   //初始化时的索引位置
        speed={1000} //页面切换速度，slider自动滑动开始到结束的时间（单位ms）
        loop={true} //是否形成环路
        spaceBetween={0} //页面直接的间距
        slidesPerView={1} //设置slider容器能够同时显示的slides数量(carousel模式)。
        //自动滚屏
        autoplay={{

          delay: 500, //自动滚屏速度
          disableOnInteraction: true,  //false: 鼠标操作屏幕后继续自动滚屏
        }}
        modules={[Navigation, Pagination, Scrollbar, A11y]}
        // navigation
        pagination={{ clickable: true }}
      >
        <SwiperSlide>
          <div className="swiper1">
            <div className='content'>
              <h2>xPhone</h2>
              <p>Lots to love.Less to spend </p>
              <p>Starting at $399.</p>
            </div>
          </div>
        </SwiperSlide>
        <SwiperSlide>
          <div className="swiper2">
            <div className='content1'>
              <h2>Tablet</h2>
              <p>Just the right amount of everything.</p>
            </div>
          </div>
        </SwiperSlide>
        <SwiperSlide>
          <div className="swiper3">
          <div className='content2'>
              <h2>Buy a Tablet or xPhone for college Get arPods</h2>
            </div>
          </div>
        </SwiperSlide>
      </Swiper>
    </div>
  )
}

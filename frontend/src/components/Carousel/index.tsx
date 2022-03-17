import React, { useState, useEffect }  from "react";
import Swiper from 'swiper';
import { SlideContainer } from './style';
import 'swiper/dist/css/swiper.css';

interface ImagProps {
    src: string,
    alt: string,
  }

interface CarouselProps {
    bannerList: Array<ImagProps>,
}

const Carousel:React.FC<CarouselProps> = (props) => {
    const [sliderSwiper, setSliderSwiper] = useState<any>(null);
    const { bannerList } = props;

    // 初始化图片
    useEffect(() => {
        if(bannerList.length && !sliderSwiper) {
            const sliderSwiper = new Swiper('.slider-container', {
                loop: true,
                autoplay: { disableOnInteraction: false, delay: 3000 },
                pagination: {
                    el: '.swiper-pagination',
                    type: 'custom',
                    renderCustom: function(_, current, total) {                      
                        let customPaginationHtml = "";
                        for(let i = 0; i < total; i++) {
                            //判断哪个分页器此刻应该被激活
                            if(i === (current - 1)) {
                                customPaginationHtml += '<span class="swiper-pagination-customs swiper-pagination-customs-active"></span>';
                            } else {
                                customPaginationHtml += '<span class="swiper-pagination-customs"></span>';
                            }
                        }
                        return customPaginationHtml;
                    }
                }
            });
            setSliderSwiper(sliderSwiper);
        }
    }, [bannerList.length]);

    return (
    <SlideContainer>
        <div className="slider-container">
            <div className="swiper-wrapper">
            {
                bannerList.map((slider) => {
                    return (
                        <div
                            className="swiper-slide"
                            key={slider.src}
                        >
                            <div className="slider-nav">
                                <img src={slider.src} width="100%" height="100%" alt={slider.alt} />
                            </div>
                        </div>
                    )
                })
            }
            </div>
            <div className="swiper-pagination"></div>
        </div>
    </SlideContainer>
    )
};

export default Carousel;
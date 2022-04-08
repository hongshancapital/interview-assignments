import React, { useEffect } from 'react';
import { SwiperViewProps, SwiperViewDefaultProps } from './swiper-props';
import { Swiper, SwiperSlide } from 'swiper/react';
import Styles from './swiper.module.scss';

const SwiperView = ({
    autoPlay,
    sliders
}: SwiperViewProps) => {
    useEffect(() => {
        console.log('###sliders--->', sliders);
    }, [sliders])
    return (
        <>
            <Swiper
                spaceBetween={50}
                slidesPerView={3}
                onSlideChange={() => console.log('##swiper changed--->')}
                onSwiper={(swiper) => console.log('##swiper--->', swiper)}
            >
                {sliders?.length ? (
                    sliders.map((slider, index) => {
                        return (
                            <SwiperSlide key={`slider_${index}`}>
                                <img className={Styles.swiper} src={slider} alt=''></img>
                            </SwiperSlide>
                        )
                    })

                ) : null}
                <SwiperSlide></SwiperSlide>
            </Swiper>
        </>
    )
}

SwiperView.defaultProps = SwiperViewDefaultProps;

export default SwiperView;
import React, { memo, useState, useEffect  } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import SwiperCore, { Navigation, Pagination, Autoplay } from 'swiper';
import p1 from '@/assets/img/p2.jpeg';
import p2 from '@/assets/img/p3.jpeg';
import p3 from '@/assets/img/p1.webp';
interface Props {
}
const View: React.FC<Props> = (props) => {
    const [currentImage, setCurrentImage] = useState(0);
    const images = [p1, p2, p3];
    return (
      <div className="w-full h-screen flex justify-center items-center">
      <Swiper
        className="w-full h-full"
        modules={[Navigation, Pagination, Autoplay]}
        spaceBetween={50}
        slidesPerView={1}
        navigation={{ clickable: true }}
        pagination={{ type: 'bullets',clickable: true }}
        loop={true}
        autoplay={{ delay: 3000 }}
      >
        {images.map((image, index) => (
          <SwiperSlide key={index}>
            <img src={image} alt="" className="w-full h-full object-cover" />
          </SwiperSlide>
        ))}
      </Swiper>
    </div>
    )
}
export default memo(View);

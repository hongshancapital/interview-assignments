import { Swiper, SwiperSlide } from 'swiper/react';
import './index.css';
import 'swiper/css';
import { Autoplay, Pagination } from 'swiper';
import 'swiper/css/pagination';

const Carsousel = () => {
  return (
    <Swiper
      spaceBetween={30}
      centeredSlides={true}
      // autoplay={{
      //   delay: 2500,
      //   disableOnInteraction: false,
      // }}
      pagination={{
        clickable: true,
      }}
      navigation={true}
      modules={[Autoplay, Pagination]}
      className="mySwiper"
    >
      <SwiperSlide style={{ backgroundColor: 'pink' }}>Slide 1</SwiperSlide>
      <SwiperSlide style={{ backgroundColor: 'skyblue' }}>Slide 2</SwiperSlide>
      <SwiperSlide style={{ backgroundColor: 'hotPink' }}>Slide 3</SwiperSlide>
    </Swiper>
  );
};

export default Carsousel;

import  React, {useState,Children} from 'react';
import Slider,{Settings} from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import "./Carousel.css";

const Carousel = (props:Settings) => {
  const [current, setCurrent] = useState<Number>(0);
  const len = Children.count(props.children);
  const defaultSettings = {
      dots: true,
      infinite: true,
      speed: 500,
      slidesToShow: 1,
      slidesToScroll: 1,
      autoplay: true,
      autoplaySpeed: 3000,
      pauseOnDotsHover: false,
      pauseOnHover: false,
      draggable: false,
      appendDots: (dots:any) => {
          return <div>{dots}</div>
      },
      customPaging: (i:number) => {
        return <div className={`line-dot ${current===i ? "active" : ""}`}></div>
      },
      beforeChange: (current:number) => setCurrent((current+1)%len)
  };
  let settings = Object.assign({},defaultSettings, props);
  return (
    <div className="carousel-root">
      <Slider {...settings}/>
    </div>
  );
}

export default Carousel;

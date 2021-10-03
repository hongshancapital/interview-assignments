import React, {
  useCallback,
  useEffect,
  useRef,
  useState
} from 'react';

import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import './styles.scss';

type SlideItem = {
  title: React.ReactNode,
  desc?: React.ReactNode,
  style: object
};

interface CarouselProps {
  duration: number,
  data: SlideItem[]
};

const Carousel: React.FC<CarouselProps> = ({
  duration = 2000,
  data
}) => {

  const sliderRef = useRef<Slider>(null);

  const [currentSlide, setCurrentSlide] = useState(-1);

  const afterChange = useCallback((index) => {
    setCurrentSlide(index);
  }, [setCurrentSlide]);

  useEffect(() => {
    if (currentSlide < 0) {
      setCurrentSlide(0);
    }

    const timeoutId = setTimeout(() => {
      if (currentSlide >= (data.length-1)) {
        sliderRef.current?.slickGoTo(0);
      }
      sliderRef.current?.slickNext();
    }, duration);

    return () => {
      clearTimeout(timeoutId);
    };
  }, [data, duration, currentSlide]);

  return (
    <div className="carousel-slider" >
      <Slider ref={sliderRef}
        dots={false}
        arrows={false}
        infinite={false}
        speed={500}
        slidesToShow={1}
        slidesToScroll={1}
        afterChange={afterChange}
      >
        {data.map((slideItem, idx) => (
          <div className="carousel-slide" key={idx} >
            <div className="content"
              style={slideItem.style}
            >
              <div className="title" >
                <h1>{slideItem.title}</h1>
                {slideItem.desc && <p>{slideItem.desc}</p>}
              </div>
            </div>
          </div>
        ))}
      </Slider>

      <div className="carousel-paging" >
        {data.map((item, i) => (
          <div className="progress-line" key={i}
            style={{
              marginRight: i === (data.length-1) ? 0 : 8
            }}
          >
            <div className={`progress-timing`}
              style={i === currentSlide ?
                {
                  width: '100%',
                  transition: `width ${duration}ms linear`
                } : undefined
              }
            />
          </div>
        ))}
      </div>
    </div>
  );
};

export default Carousel;

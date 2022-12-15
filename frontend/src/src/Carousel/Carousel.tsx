import Carousel from '../../component/carousel/carousel';
import { sliderList } from './data';
import './index.css';

export type SlideItem = {
  id: number;
  title: string;
  subTitle?: string;
  desc?: string;
  image?: string;
  bgColor?: string;
  color?: string;
};

// -- 渲染 Carousel-item-info --
function CarouselItemInfo(slide: SlideItem) {
  const {
    title,
    subTitle,
    desc,
    image,
    bgColor = "#fff",
    color = "#000",
  } = slide;

  return (
    <div
      className="carousel-item"
      style={{ backgroundColor: bgColor, color: color }}
    >
      {/* --- header --- */}
      <div className="carousel-item-header">
        <div className="carousel-item-title">{title}</div>
        {subTitle && <div className="carousel-item-subtitle">{subTitle}</div>}
        {desc && <div className="carousel-item-desc">{desc}</div>}
      </div>

      {/* --- image ---- */}
      {image && (
        <div className="carousel-item-bottomImg">
          <div
            className="carousel-item-bg"
            style={{ backgroundImage: `url(${image})` }}
          ></div>
        </div>
      )}
    </div>
  );
};

function CarouselPage() {
  return (
    <div className='page-carousel'>
      <Carousel duration={3000}>
        {sliderList.map((slide) => {
          return (
            <CarouselItemInfo key={slide.id} {...slide}></CarouselItemInfo>
          )
        })}
      </Carousel>
    </div>
  );
}

export default CarouselPage;
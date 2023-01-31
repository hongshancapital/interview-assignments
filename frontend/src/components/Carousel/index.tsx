import classnames from 'classnames';
import './index.css';
import useCarousel from './useCarousel';

export interface ICarouselItem {
  /**
   * 图片url
   */
  img: string;

  /**
   * 标题
   */
  titles: string[];

  /**
   * 描述
   */
  descs?: string[];

  /**
   * 描述
   */
  wrapperStyle?: React.CSSProperties;
}

export interface ICarouselProps {
  /**
   * 数据
   */
  data: ICarouselItem[];

  /**
   * 持续时间(ms)
   */
  duration?: number;

  /**
   * 默认展示的index值
   */
  defaultActiveIndex?: number;
}

export interface ICarouselDot {
  /**
   * 是否是当前的值
   */
  isActive: boolean;

  /**
   * 持续时间(ms)
   */
  duration: number;
}

const CarouselItem = ({ img, titles, descs, wrapperStyle }: ICarouselItem) => {
  return (
    <div className="carousel-item" style={wrapperStyle}>
      <div className="carousel-title">
        {titles.map((title, id) => {
          return <div key={`carousel_title_${id}`}>{title}</div>;
        })}
      </div>
      <div className="carousel-desc">
        {descs?.map((desc, id) => {
          return <div key={`carousel_desc_${id}`}>{desc}</div>;
        })}
      </div>
      <div
        className="carousel-img"
        style={{ backgroundImage: `url(${img})` }}
      />
    </div>
  );
};

const CarouselDot = ({ isActive, duration }: ICarouselDot) => {
  return (
    <div className="carousel-dot">
      <div
        className={classnames('carousel-progress', isActive ? 'active' : '')}
        style={{ animationDuration: `${duration / 1000}s` }}
      ></div>
    </div>
  );
};

const Carousel = ({
  data,
  duration = 3000,
  defaultActiveIndex = 0,
}: ICarouselProps) => {
  const activeIndex = useCarousel({
    length: data.length,
    duration,
    defaultActiveIndex,
  });
  return (
    <div className="carousel-container">
      <div
        className="carousel-content"
        style={{ transform: `translate(-${activeIndex * 100}vw)` }}
      >
        {data.map((item, id) => {
          return <CarouselItem {...item} key={`carousel_item_${id}`} />;
        })}
      </div>
      <div className="carousel-dots">
        {data.map((item, id) => {
          return (
            <CarouselDot
              isActive={id === activeIndex}
              duration={duration}
              key={`carousel_dot_${id}`}
            />
          );
        })}
      </div>
    </div>
  );
};

export default Carousel;

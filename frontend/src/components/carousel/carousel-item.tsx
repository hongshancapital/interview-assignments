import { CarouselItemProps } from '.';

const CarouselItem = ({
  width,
  title,
  img,
  description,
  carouselItemProps,
}: CarouselItemProps) => (
  <div
    className="carousel-item"
    {...carouselItemProps}
    style={{
      ...carouselItemProps?.style,
      width,
    }}
  >
    <h2 className='title'>{title}</h2>
    <p className='text'>{description}</p>
    <img src={img} alt="" />
  </div>
);

export default CarouselItem;

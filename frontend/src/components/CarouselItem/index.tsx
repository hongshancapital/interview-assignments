import classNames from 'classnames';

import './index.scss';

export interface ICarouselItemProps {
  title: string;
  describe?: string;
  className?: string;
}


const CarouselItem: React.FC<ICarouselItemProps> = ({ title, describe, className }) => {
  return <div className="carousel-item">
    <div className={classNames('carousel-item-content', className)}>
      <h2 className="title">{title}</h2>
      {
        describe && <p className="descript">{describe}</p>
      }
    </div>
  </div>
};

export default CarouselItem;
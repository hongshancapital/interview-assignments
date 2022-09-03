import { CSSProperties, default as React, ReactNode, useState } from 'react';
import './Carousel.scss';
import { CarouselItemHooks } from './CarouselItemHooks';
import ProgressHooks from './ProgressHooks';

interface ICarouselProps {
  /**
   * 数据源
   */
  dataSource: ReactNode[];

  /**
   * 每张轮播播放的毫秒数，默认2000
   */
  interval?: number;
}

const CarouselHooks: React.FC<ICarouselProps> = (props) => {
  const [selectedIndex, setSelectedIndex] = useState(0);

  const renderFooter = () => {
    const { dataSource, interval = 2000 } = props;
    if (!dataSource || !dataSource.length) {
      return null;
    }

    return (
      <footer>
        {dataSource.map((_, index) => {
          const selected = index === selectedIndex;
          return (
            <ProgressHooks
              interval={interval}
              key={index}
              running={selected}
              onClick={() => {
                setSelectedIndex(index);
              }}
              onEnd={() => {
                setSelectedIndex(validateIndex(selectedIndex + 1));
              }}
            />
          );
        })}
      </footer>
    );
  };

  const validateIndex = (value: number) => {
    const { dataSource } = props;
    if (!value || !dataSource || !dataSource.length) {
      return 0;
    }
    return value % dataSource.length;
  };

  const renderContent = () => {
    const { dataSource } = props;

    if (!dataSource || !dataSource.length) {
      return null;
    }
    const style: CSSProperties = {
      transform: `translateX(-${selectedIndex * 100}%)`,
    };
    return (
      <main style={style}>
        {dataSource.map((item, index) => {
          return <CarouselItemHooks key={index}>{item}</CarouselItemHooks>;
        })}
      </main>
    );
  };

  return (
    <div className="Carousel">
      {renderContent()}
      {renderFooter()}
    </div>
  );
};

export default CarouselHooks;

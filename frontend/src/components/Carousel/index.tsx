import {
  Children,
  FC,
  ReactElement,
  ReactNode,
  useCallback,
  useMemo,
  useState,
} from 'react';
import Indicator from './Indicator';
import styles from './index.module.scss';

interface CarouselItemProps {
  children: ReactNode;
}

interface CarouselProps {
  autoplay?: boolean;
  children: ReactElement<CarouselItemProps> | ReactElement<CarouselItemProps>[];
}

const CarouselItem: FC<CarouselItemProps> = (props) => null;

const Carousel: FC<CarouselProps> & { Item: typeof CarouselItem } = ({
  autoplay = false,
  children,
}) => {
  const [cur, setCur] = useState(0);

  const items = Children.map(children, (child, index) => {
    if (child.type !== CarouselItem) {
      return null;
    }
    return (
      <div key={index} className={styles.item}>
        {child.props.children}
      </div>
    );
  }).filter((item) => item);

  const total = items.length;

  const style = useMemo(() => {
    return {
      width: `${100 * total}%`,
      transform: `translate(-${(100 * cur) / total}%, 0)`,
    };
  }, [cur, total]);

  const next = useCallback(() => {
    setCur(cur < total - 1 ? cur + 1 : 0);
  }, [cur, total]);

  return (
    <div className={styles.carousel}>
      <div className={styles.items} style={style}>
        {items}
      </div>
      <div className={styles.indicator}>
        <Indicator
          total={total}
          cur={cur}
          onIndexSelected={setCur}
          onTimingEnd={autoplay ? next : undefined}
        />
      </div>
    </div>
  );
};

Carousel.Item = CarouselItem;

export default Carousel;

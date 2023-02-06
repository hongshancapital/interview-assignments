import { PropsWithChildren } from 'react';
import styles from './styles/CarouselCard.module.scss';

const CarouselCard: React.FC<
  PropsWithChildren<{ backgroundColor: string; backgroundImage: string }>
> = ({ backgroundColor, backgroundImage, children }) => (
  <div
    className={styles.container}
    style={{
      backgroundColor,
      backgroundImage: `url(${backgroundImage})`
    }}
  >
    {children}
  </div>
);

export default CarouselCard;

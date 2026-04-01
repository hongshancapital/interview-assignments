import { FC } from 'react';
import styles from './index.module.css';
import { CarouselItemProps } from '../CarouselItem';

interface CarouselProgressProps {
    carouselItems: CarouselItemProps[];
    delay: number;
    curIndex: number;
}

const CarouselProgress: FC<CarouselProgressProps> = ({carouselItems, delay, curIndex}) => {
    return <div className={styles.progressContainer}>
        {carouselItems.map((item, index) => <div key={item.key} className={styles.progressItem}>
            <div className={`${styles.progressItemFill} ${curIndex === index ? styles.active : ''} `} 
                 style={{animationDuration: `${delay / 1000}s` }} />
        </div>)}
    </div>
}

export { CarouselProgress }
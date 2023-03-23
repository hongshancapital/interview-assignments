import { FC } from 'react';
import { BLACK_COLOR } from '../../constants/color';
import { nanoid } from 'nanoid';
import styles from './index.module.css';

interface CarouselItemProps {
    key: string;
    title: string[];
    backgroundImage: string;
    backgroundColor: string;
    description?: string[];
    fontColor?: string;
}

const CarouselItem: FC<CarouselItemProps> = ({title, backgroundImage, description = [], fontColor = BLACK_COLOR, backgroundColor}) => {
    return <div className={styles.carouselItem} style={{backgroundImage: `url(${backgroundImage})`, color: fontColor, backgroundColor}}>
        <div className={styles.textContainer}>
            <div className={styles.title}>{title.map(curTitle => <div key={nanoid()}>{curTitle}</div>)}</div>
            {description.length ? <div className={styles.description}>{description.map(curDescription => <div key={nanoid()}>{curDescription}</div>)}</div> : null}
        </div>
    </div>
}

export { CarouselItem }
export type { CarouselItemProps }
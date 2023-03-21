import { FC } from 'react';
import { BLACK_COLOR } from '../../constants/color';
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
            <div className={styles.title}>{title.map((curTitle, index) => <div key={index}>{curTitle}</div>)}</div>
            {description.length ? <div className={styles.description}>{description.map((curDescription, index) => <div key={index}>{curDescription}</div>)}</div> : null}
        </div>
    </div>
}

export { CarouselItem }
export type { CarouselItemProps }
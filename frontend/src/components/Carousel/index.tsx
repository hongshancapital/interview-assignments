import { FC, useState, useEffect } from 'react';
import styles from './index.module.css';
import { CarouselItem, CarouselItemProps } from '../CarouselItem';
import { CarouselProgress } from '../CarouselProgress';

interface CarouselProps {
    carouselData: CarouselItemProps[];
    delay?: number;
    autoPlay?: boolean;
}

const Carousel: FC<CarouselProps> = ({carouselData, delay = 3000, autoPlay = true}) => {
    const [curIndex, setCurIndex] = useState(0);
    
    const goToNextItem = () => {
        if (carouselData.length < 2) {
            return
        }
        setCurIndex(curIndex + 1 === carouselData.length ? 0 : curIndex + 1)
    }

    useEffect(() => {
        if (!autoPlay) {
            return
        }
        let timer = setTimeout(goToNextItem, delay)
        return () => clearTimeout(timer)
    }, [curIndex])

    return <div className={styles.container}> 
        <div className={styles.imageList} style={{width: `${carouselData.length * 100}%`, transform: `translateX(-${curIndex * 100 / carouselData.length}%)`}}>
            {carouselData.map(item => <CarouselItem key={item.key} title={item.title} backgroundImage={item.backgroundImage} description={item.description} fontColor={item.fontColor} backgroundColor={item.backgroundColor} />)}
        </div>
        <CarouselProgress carouselItems={carouselData} delay={delay} curIndex={curIndex} />
    </div>
}

export { Carousel }
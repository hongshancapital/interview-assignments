import Slider from '../../ui/Slider'
import styles from './styles.module.css'

interface ICarouselItem {
    title: React.ReactNode;
    text: React.ReactNode;
    imgSrc: string;
    style?: React.CSSProperties;
}

interface CarouselProps {
    items: ReadonlyArray<ICarouselItem>;
    duration?: number;
}

const Carousel = ({ items, duration }: CarouselProps) => {
    return (
        <Slider duration={duration}>
            {
                items.map(({ title, text, imgSrc, style }, index) => (
                    <div
                        key={index}
                        style={{ backgroundImage: `url(${imgSrc})`, ...style }}
                        className={styles['item']}
                    >
                        <div className={styles['title']}>{title}</div>
                        <div className={styles['text']}>{text}</div>
                    </div>
                ))
            }
        </Slider>
    )
}

export default Carousel
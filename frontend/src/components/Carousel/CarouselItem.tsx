import styles from './carousel.module.css'

/**
 * Carousel Item Component
 * @param props 
 * @returns 
 */
function CarouselItem(props: SCDT.ICarouselItemOption) {
    return (
        <div className={styles['carousel-item']} style={{
            backgroundImage: `url(${props.backgroundImage})`
        }}>
            <div className={styles['carousel-item__slogan']}>
                {props.slogan}
            </div>
            <div className={styles['carousel-item__logo']} style={{
                backgroundImage: `url(${props.backgroundImage})`,
            }}>
            </div>
        </div>
    )
}

export default CarouselItem
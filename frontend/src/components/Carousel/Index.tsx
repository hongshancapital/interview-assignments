import CarouselItem from './CarouselItem'
import Indicator from './Indicator'
import styles from './carousel.module.css'
import { useEffect, useState } from 'react'

/**
 * Carousel Component
 * @param props 
 * @returns 
 */
function Carousel(props: SCDT.ICarouselOption): JSX.Element {
    const [index, setIndex] = useState(props.index || 0)
    const [screenWidth, setScreenWidth] = useState(window.innerWidth)

    const next = (index: number) => {
        setIndex(() => {
            return index % props.items.length
        })
    }

    useEffect(() => {
        const resizeCallback = () => {
            setScreenWidth(() => {
                return window.innerWidth
            })
        }

        window.addEventListener('resize', resizeCallback)
        return () => {
            window.removeEventListener('resize', resizeCallback)
        }
    })

    return <div className={styles.carousel}>
        <div
            className={styles['carousel-inner']}
            style={{
                'width': `${screenWidth * props.items.length}px`,
                'transform': `translate(-${(index) * screenWidth}px, 0)`
            }}
        >
            {
                (props.items || [])
                    .map((v: SCDT.ICarouselItemOption, i: number) => (
                        <CarouselItem {...v} key={i}/>
                    ))
            }
        </div>

        <Indicator
            count={props.items.length}
            index={index}
            duration={props.duration}
            onNext={(index: number) => {
                next(index)
            }}
        />
    </div>
}

export default Carousel
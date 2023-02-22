import { useEffect, useState } from 'react'
import styles from './carousel.module.css'

/**
 * Indicator Component
 * @param props 
 * @returns 
 */
function Indicator(props: SCDT.IIndicatorOption) {
    const [index, setIndex] = useState(props.index || 0)

    const next = (targetIndex?: number) => {
        setIndex((index) => {
            return targetIndex === undefined
                ? (index + 1) % props.count
                : targetIndex
        })
    }

    useEffect(() => {
        const animationstartCallback = (e: Event) => {
            console.log(1234)
            props.onNext(index)
        }

        const animationendCallback = (e: Event) => {
            console.log(456)
            next()
        }

        document.addEventListener('animationstart', animationstartCallback)
        document.addEventListener('animationend', animationendCallback)

        return () => {
            document.removeEventListener('animationstart', animationstartCallback)
            document.removeEventListener('animationend', animationendCallback)
        }
    })

    return <div className={styles.indicator}>
        {
            (Array(props.count).fill(0)).map((v: number, i: number) => {
                return (
                    <div
                        className={`${styles['indicator-item']} ${index === i ? styles['indicator-item--active'] : '' }`}
                        onClick={() => {
                            next(i)
                        }}
                        key={i}
                    >
                        <div
                            className={styles['indicator-item__mask']}
                            style={{
                                ...(index === i) ? {
                                    'animation': `moving ${props.duration / 1000}s linear`
                                } : {}
                            }}></div>
                    </div>
                )
            })
        }
    </div>
}

export default Indicator
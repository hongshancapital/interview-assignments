import { useState, useRef, useLayoutEffect, useEffect, cloneElement } from 'react'
import Progress from '../Progress/index';
import styles from './styles.module.css'

interface ISliderProps {
    children: ReadonlyArray<React.ReactNode>;
    duration?: number;
}

function Slider({ children, duration = 2 }: ISliderProps) {
    const [targetIndex, setTargetIndex] = useState(0)
    const [width, setWidth] = useState(0)
    const container = useRef<HTMLDivElement>(null)
    const track = useRef<HTMLDivElement>(null)

    useLayoutEffect(() => {
        const { offsetWidth = 0 } = (container?.current) as HTMLDivElement
        setWidth(offsetWidth)
    }, [])

    useEffect(() => {
        const id = setInterval(() => {
            animat()
        }, duration * 1000)

        return () => clearInterval(id)
    }, [targetIndex])

    const animat = () => {
        if (track.current) {
            let index = targetIndex + 1

            if (index > children.length && track.current) {
                track.current.style.transitionDuration = ''
                track.current.style.transform = `translate3d(0px, 0px, 0px)`
                setTimeout(() => {
                    if (track.current) {
                        track.current.style.transitionDuration = `0.5s`
                        index = 1
                        setTargetIndex(index)
                    }
                }, 30)
                return
            }

            setTargetIndex(index)
        }
    }

    if (Array.isArray(children)) {
        const first = cloneElement(children[0])
        return (
            <div className={styles['wrapper']}>
                <div
                    ref={container}
                    className={styles['container']}
                >
                    <div
                        ref={track}
                        className={styles['slider-track']}
                        style={{ width: width * (children.length + 1), transform: `translate3d(-${targetIndex * width}px, 0px, 0px)`, transitionDuration: `0.5s` }}
                    >
                        {children.map((item, i) => (
                            <div
                                key={i}
                                className={styles['chunck']}
                                style={{ width }}
                            >
                                {item}
                            </div>
                        ))}
                        <div className={styles['chunck']} style={{ width }}>{first}</div>
                    </div>
                </div>
                <div className={styles['dot-container']}>
                    {
                        children.map((item, i) => (
                            <div key={i} className={styles['dot']}>
                                <Progress
                                    key={i}
                                    duration={duration}
                                    rate={(i === targetIndex || (i === 0 && targetIndex === children.length)) ? '100%' : 0}
                                />
                            </div>
                        ))
                    }
                </div>
            </div>
        )
    }

    return null
}

export default Slider
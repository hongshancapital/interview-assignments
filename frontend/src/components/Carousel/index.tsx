import { useEffect, useState, useRef, ReactElement } from 'react';
import { animateObj, ICarouselProps } from './types';
import styles from './index.module.scss';
import React from 'react';

const Carousel: React.FC<ICarouselProps> = (props) => {
    const {
        scrollDirection = 'column',
        onPageChanged,
        initialPage = 0,
        children
    } = props;

    let pageWidth = useRef(0);
    let X = 0;
    const oldCurrentPage = useRef(0);
    const contentRef = useRef<animateObj>();
    const getcontentRef = () => contentRef.current as HTMLDivElement;

    const setMove = (moveX: number) => {
        return getcontentRef().style.transform = `translate3d(${moveX}px,0px,0px)`
    }

    const [currentPage, setCurrentPage] = useState<number>(initialPage);
    

    useEffect(() => {
        pageWidth.current = document.documentElement.clientWidth;
        console.log(children.length);
        
        const timer = setInterval(() => {
                if (oldCurrentPage.current === children.length - 1) {
                    oldCurrentPage.current = 0;
                    setCurrentPage(oldCurrentPage.current)
                    backStartMove()
                } else {
                    oldCurrentPage.current += 1;
                    setCurrentPage(oldCurrentPage.current)
                    forwardMove()
                    
                }
        }, 3000);
        return () => {
            clearInterval(timer);
        }
    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])

    const forwardMove = () => {
        getcontentRef().style.transition = ".3s ease all"
        X = -oldCurrentPage.current * pageWidth.current
        setMove(X)
    }

    const backStartMove = () => {
        getcontentRef().style.transition = "1s ease all"
        X = oldCurrentPage.current * pageWidth.current
        setMove(X)
    }

    return (
        <div className={styles.carouselWrapper}>
            <div ref={contentRef as any} className={styles.content} style={{
                flexDirection: scrollDirection,
                width: `${(children.length+1)*100}%`,
            }}>
                {children.map(item => item)}
            </div>
            <div className={styles.dots}>
                {
                    children.map((_, index) => (
                        <div className={styles.dot}>
                            <div className={currentPage === index? styles.animate : styles.load}></div>
                        </div>
                    ))
                }
            </div>
        </div>
    )
}

export default Carousel;
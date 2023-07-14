import React, { PropsWithChildren, useEffect, useState, Children, useMemo, CSSProperties } from "react"
import { Pagination } from "./Pagination";
import { CarouselContext } from './Context'
import { CarouSelProps } from "./types";



const CarouSel: React.FC<PropsWithChildren<CarouSelProps>> = ({ children, ...props }) => {
    const { autoPlay = true, autoPlayInterval = 5, transitionDuration = 1 } = props
    const [currentSlideIndex, setCurrentSlideIndex] = useState(0);
    const slidesCount = useMemo(() =>
        Children.toArray(children).length
        , [children])

    useEffect(() => {
        if (autoPlay) {
            const timer = setInterval(() => {
                setCurrentSlideIndex(preIndex => preIndex === slidesCount - 1 ? 0 : preIndex + 1)
            }, autoPlayInterval * 1000)
            return () => clearInterval(timer)
        }

    }, [autoPlay, autoPlayInterval, slidesCount])
    const slideContainerStyle = useMemo<CSSProperties>(() => {
        return {
            width: `${slidesCount * 100}%`,
            transform: `translate(-${currentSlideIndex * 100 / slidesCount}%)`,
            transitionDuration: `${transitionDuration}s`
        }
    }, [slidesCount, currentSlideIndex, transitionDuration])

    return (
        <CarouselContext.Provider value={{
            activedIndex: currentSlideIndex,
            slidesCount,
            autoPlay,
            autoPlayInterval,
            transitionDuration
        }}>
            <div className="container">
                <div className="slideContainer" style={slideContainerStyle}>
                    {
                        Children.toArray(children).map((element, index) =>
                            <div
                                key={index}
                                style={{ width: `${100 / slidesCount}%` }}
                                className={`slide${index === currentSlideIndex ? ' actived' : ''}`}
                                data-testid={`carousel-slide-index-${index}`}
                                data-slide-actived={index === currentSlideIndex}>
                                {element}
                            </div>)
                    }
                </div>
                <Pagination />
            </div>
        </CarouselContext.Provider>)
}

export { CarouSel }
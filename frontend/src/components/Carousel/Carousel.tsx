import React, { useCallback, useEffect, useState } from "react";
import CarouselIndicator from "./Indicator/CarouselIndicator";
import Slide from "./Slide/Slide";
import { carouselItem, indicatorBackgroundColor } from "./CarouselModel";
import './Carousel.scss'

interface IProps {
    carouselItems: carouselItem[];
}

// Component of carousel which configured by props
const Carousel:React.FC<IProps> = (props) => {
    const [slices, ] = useState<carouselItem[]>(props.carouselItems);
    const [curIndex, setCurIndex] = useState<number>(0);
    const [curIndicatorColor, setCurIndicatorColor] = useState<indicatorBackgroundColor | null | undefined>(() => props.carouselItems[0].indicator ? props.carouselItems[0].indicator : null);

    // uesCallback to forbidden indicator unused rerender
    const onFinished = useCallback((sliceNumber:number) => {
        if(sliceNumber === 2) {
            updatePage(0);
        } else {
            updatePage(sliceNumber+1);
        }
    }, []);

    const onClick = useCallback((sliceNumber:number) => {
        updatePage(sliceNumber);
    }, [])

    // update page index and indicator processbar color
    function updatePage(index:number) {
        setCurIndex(index);
        if(props.carouselItems[index].indicator) {
            setCurIndicatorColor(props.carouselItems[index].indicator);
        }
    }
    
    function isCarouselConfigEmpty():boolean {
        let isEmpty = true;
        for(const slice of slices) {
            if(slice.titles && slice.descriptions) {
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
    }

    if(isCarouselConfigEmpty()) {
        return (
            <h2 className="carousel_config_empty">
                Please check your carousel configure.
            </h2>
        )
    }

    return (
        <div className="carousel">
            <div className="slide_container">
                <Slide carouselItems={slices} curIndex={curIndex}></Slide>
            </div>
            <div className="carousel_indicator_container">
                {
                    slices.map((slice, index) => {
                        return <CarouselIndicator
                                    key={index}
                                    sliceNumber={index}
                                    startCountDown={index === curIndex}
                                    onClick={onClick}
                                    onFinished={onFinished}
                                    processColor={curIndicatorColor}
                                />
                    })
                }
            </div>
        </div>
    )
}

export default Carousel;
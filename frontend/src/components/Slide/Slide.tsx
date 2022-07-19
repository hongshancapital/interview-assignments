/**
 * @version 0.0.1
 * @author curryfu
 * @date 2022-03-30
 * @description 纯轮播图UI
 */
import React from "react";
import "./Slide.css";
import {CarouselBannerConfig, CarouselBannerConfigInter, ContentItem} from "../../config/Carousel";

interface SlideProps {
    index?: number;
    transitionTime?: number;
}

const Indicator: React.FC<SlideProps> = (props: SlideProps) => {
    const {index = 0, transitionTime = 1000} = props;
    return (
        <div className="wrap" style={{left: `-${100 * index}vw`, transition: `left ease ${transitionTime}ms`}}>
            {
                CarouselBannerConfig.map((value: CarouselBannerConfigInter) => {
                    return (
                        <div className="banner" key={value.id} style={{background: value.backgroundColor}}>
                            {value.id === 'xPhone' ?
                                <img src={value.imageUrl} alt="Tablet or xPhone" style={{objectFit: 'contain'}}/> :
                                <img src={value.imageUrl} alt="Tablet or xPhone" style={{objectFit: 'cover'}}/>}

                            <div className="text">
                                {
                                    value.content.map((value: ContentItem) => {
                                        return (
                                            <span style={{...value.style}} key={value.id}>
                            {value.name}
                                </span>
                                        )
                                    })
                                }
                            </div>
                        </div>
                    )
                })
            }
        </div>
    )
};

export default Indicator;
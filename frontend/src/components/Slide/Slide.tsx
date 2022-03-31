import React from "react";
import "./Slide.css";
import { CarouselBannerConfig } from "../../config/Carousel";
interface SlideProps {
    index?: number;
    transitionTime?: number;
}
const Indicator: React.FC<SlideProps> = (props: SlideProps) => {
    const { index = 0, transitionTime = 1000 } = props;
    return (
        <div className="wrap" style={{ left: `-${100 * index}vw`, transition: `left ease ${transitionTime}ms` }}>
            {
                CarouselBannerConfig.map((value: any) => {
                    return (
                        <div className="banner" key={value.id}>
                            <img src={value.imageUrl} alt="Tablet or xPhone"/>
                            <div className="text">
                            {
                                value.content.map((value: any) => {
                                    return (
                                        <span style={{...value.style}}>
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
            {/* <div className="start">
                <span>
                    xPhone
                </span>
                <span>
                    Lots to love. Less to spend.
                </span>
                <span>
                    Starting at $399.
                </span>
            </div>
            <div className="middle">
                <span>
                    Tablet
                </span>
                <span>
                    Just the right amount of everything
                </span>
            </div>
            <div className="end">
                <span>
                    Buy a Tablet or xPhone for college.
                </span>
                <span>
                    Get arPods.
                </span>
            </div> */}
        </div>
    )
};

export default Indicator;
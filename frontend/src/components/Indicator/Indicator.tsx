import React from "react";
import {CarouselBannerConfig} from "../../config/Carousel";
import "./Indicator.css";

interface Progress {
    val: number,
    id: number
}

interface IndicatorProps {
    index?: number;
    delay?: number;
    indicatorIsShow?: boolean;
}

const Indicator: React.FC<IndicatorProps> = (props: IndicatorProps) => {
    const indicatorArr = new Array(CarouselBannerConfig.length).fill(1).map((_, index) => {
        return {'id': index, 'val': index}
    });
    const {index = 0, delay = 3000, indicatorIsShow = true} = props;
    return (
        <>
            {
                indicatorIsShow ? <div className="progress">
                    {
                        indicatorArr.map((value: Progress) => {
                            return <div
                                className="long"
                                key={value.id}
                            >
                                <div className="short"
                                     style={{
                                         width: index === value.val ? '100%' : 0,
                                         transition: index === value.val ? `width ease ${delay}ms` : 'none',
                                     }}/>
                            </div>
                        })
                    }
                </div> : null
            }
        </>
    )
}

export default Indicator;

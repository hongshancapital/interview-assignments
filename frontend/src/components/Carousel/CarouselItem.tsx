import React, { FC } from "react";
import classNames from "classnames";

export interface ICarouselItemProps {
    title: string[];
    desc?: string[];
    img: string;
    titleColor: string;
    bgColor: string
}

const CarouselItem: FC<ICarouselItemProps> = (props) => {
    const {title, desc, img, titleColor, bgColor} = props;
    const commonCls = 'carousel-item';
    
    return (
        <div
            className={classNames(commonCls)}
            style={{
                backgroundImage: `url(${img})`,
                backgroundColor: bgColor,
                color: titleColor,
            }}
        >
            <div 
                className={classNames(`${commonCls}-content`)}
            >
                <div className={classNames(`${commonCls}-title-container`)}>
                    {
                        title.map((item, index) => (
                            <span key={`title-${index}`} className={classNames(`${commonCls}-title`)}>{item}</span>
                        ))
                    }
                </div>
                {desc &&
                    <div className={classNames(`${commonCls}-desc-container`)}>
                        {
                            desc.map((item, index) => (
                                <span key={`desc-${index}`} className={classNames(`${commonCls}-desc`)}>{item}</span>
                            ))
                        }
                    </div>
                }
            </div>
        </div>
    )
}

export default CarouselItem
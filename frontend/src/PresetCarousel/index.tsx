import React, {FC, ReactNode} from 'react';
import Carousel from "../Carousel";
import s from './index.module.css';

interface TProps {
    data: Array<{
        key: string;
        className?: string;
        titleClassName?: string;
        title: ReactNode;
        subTitle: ReactNode;
        imgSrc: string;
    }>
}

const PresetCarousel: FC<TProps> = (props) => {
    return (
        <Carousel className={s.carousel}>
            {props.data?.map((d) => (
                <Carousel.Page key={d.key} className={d.className}>
                    <div className={`${s.text} ${d.titleClassName}`}>
                        <div className={s.title}>{d.title}</div>
                        <div className={s.subTitle}>{d.subTitle}</div>
                    </div>
                    <img className={s.img} src={d.imgSrc} alt=""/>
                </Carousel.Page>
            ))}
        </Carousel>
    );
}

export default PresetCarousel;
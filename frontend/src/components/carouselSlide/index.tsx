import React, { Fragment } from "react";
import classNames from "classnames/bind";
import styles from './index.scss';
import { ICarouseData, ICarouselSlideProps } from "../../types/carousel";


const cx = classNames.bind(styles);

const HsCarouselSlide = (props: ICarouselSlideProps) => {

    return (
        <Fragment>
            <ul className={cx('carouseSlidecontainer')} style={{ transform: `translateX(${props.selectIndex * 100 * (-1)}vw)`  }}>
                {
                    props.data.map((value: ICarouseData) => {

                        return (
                            <div className={cx('slideImage')} style={{ backgroundImage: `url('${value.path}')`,color:value.fontColor }}>
                                <p className={cx('slideTitle')}>{value.title}</p>
                                <p className={cx('slideContent')}>{value.content}</p>
                            </div>
                        )
                    })
                }
            </ul>
        </Fragment>
    )
}
export default HsCarouselSlide;
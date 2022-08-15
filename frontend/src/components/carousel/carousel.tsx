/*
 * @Author: longsi
 * @Date: 2022-06-10 19:26:32
 * @LastEditors: longsi
 * @LastEditTime: 2022-06-14 15:53:52
 * @FilePath: /interview-assignments/frontend/src/components/carousel/carousel.tsx
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import cn from 'classnames';
import React from 'react';

import styles from './css/carousel.module.scss';
import { Dot } from './dot';
import { useCarousel } from './use-carousel';

import type { CSSProperties } from 'react';

interface ICarouselProps {
    interval?: number | null;
    children: JSX.Element[];
    className?: string;
    style?: CSSProperties;
    pauseOnHover?: boolean;
    dotClickable?: boolean;
}

const noop = () => { };

export const Carousel: React.FC<ICarouselProps> = ({
    interval = 3000,
    pauseOnHover = false,
    dotClickable = false,
    children,
    className,
    style
}) => {
    const count = React.Children.count(children);
    const { current, carouselGoTo, play, pause, trackStyle, slideStyle } = useCarousel(count, interval);
    const firstChild = React.Children.toArray(children)[0];
    return (
        <div
            className={cn(className, styles.carousel)}
            style={style}
            onMouseEnter={pauseOnHover ? pause : noop}
            onMouseLeave={pauseOnHover ? play : noop}
        >
            <div className={styles['carousel-list']}>
                <div className={styles['carousel-track']} style={trackStyle}>
                    {
                        React.Children.map(children, (child: JSX.Element) => {
                            return (
                                <div
                                    key={child?.key}
                                    className={styles['carousel-slide']}
                                    style={slideStyle}
                                >
                                    {child}
                                </div>
                            );
                        })
                    }
                    {/* cloned slide */}
                    <div
                        key="carousel-slide-cloned"
                        className={styles["carousel-slide"]}
                        style={slideStyle}
                    >
                        {React.cloneElement(firstChild as JSX.Element)}
                    </div>
                </div>
            </div>
            <div className={styles["carousel-dots"]}>
                {React.Children.map(children, (child: JSX.Element, index) => {
                    return (
                        <Dot
                            key={child?.key}
                            interval={interval}
                            active={current === index}
                            onClick={
                                () => {
                                    if (dotClickable) {
                                        carouselGoTo(index)
                                    }
                                }
                            }
                        />
                    );
                })}
            </div>
        </div>
    );
}
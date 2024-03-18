import { useLayoutEffect, useRef, useState } from 'react';
import "./index.css";
import { CarouselData, CarouselProps } from './interface';

const Carousel = (props: CarouselProps) => {

    const { dataSource, autoPlay, showSwitch, children } = props;
    const [currentIndex, setCurrentIndex] = useState(0);
    const [width, setWidth] = useState(0);
    const renderRef = useRef(true);
    const carouselRef = useRef(null);
    let timerRef = useRef<any>(null);
    const delay = autoPlay?.delay || 3000;
    const size = dataSource.length;

    // 自动播放
    const autoPlayFn = () => {
        timerRef.current = setInterval(() => {
            setCurrentIndex((pre: number) => {
                if (pre >= (size - 1)) {
                    pre = 0;
                } else {
                    ++pre;
                }
                return pre;
            });
        }, delay);
    };

    // 跳转到指定页
    const toIndex = (index: number) => {
        clearInterval(timerRef.current);
        setCurrentIndex(index);
        autoPlay && autoPlayFn();
    }

    // 上一页、下一页
    const prevOrNext = (action: 'prev' | 'next') => {
        if (action === 'next') {
            setCurrentIndex((pre: number) => {
                if (pre === (size - 1)) {
                    return 0;
                } else {
                    return ++pre;
                }
            });
        }
        if (action === 'prev') {
            setCurrentIndex((pre: number) => {
                if (pre === 0) {
                    return (size - 1);
                } else {
                    return --pre;
                }
            });
        }
    }

    // 组件初次渲染
    useLayoutEffect(() => {
        if (renderRef.current) {
            renderRef.current = false;
            return;
        }
        const resizeObserver = new ResizeObserver((entries) => {
            for (const entry of entries) {
                setWidth(entry.contentRect.width);
            }
        });
        carouselRef.current && resizeObserver.observe(carouselRef.current);

        (autoPlay || (autoPlay?.delay > 0)) && autoPlayFn();

        document.addEventListener("visibilitychange", (e: Event) => {
            if (document.visibilityState === 'visible') {
                (autoPlay || (autoPlay?.delay > 0)) && autoPlayFn();
            } else {
                (autoPlay || (autoPlay?.delay > 0)) && clearInterval(timerRef.current);
            }
        })
        return () => {
            resizeObserver.disconnect();
        }
    }, [])


    return (
        <div className="container" ref={carouselRef}>
            <div className="carousel clearfix"
                style={{ transform: `translateX(${-currentIndex * width}px)`, width: (size * width) + 'px' }}>
                {
                    children ? children : (size > 0 && dataSource.map((item: CarouselData, i: number) => (
                        <div key={`item-${i}`} className="item" style={{ 'backgroundImage': `url(${item.imgUrl})`, width: width + 'px', ...item.style }}>
                            <div className="title">{item?.title}</div>
                            <div className="text">{item?.content}</div>
                        </div>
                    )))
                }
            </div>
            {showSwitch && <>
                <i className="switch prev" onClick={() => prevOrNext('prev')}>{'<'}</i>
                <i className="switch next" onClick={() => prevOrNext('next')}>{'>'}</i>
            </>}
            <div className="activity">
                {
                    size > 0 && dataSource.map((item: CarouselData, i: number) => (
                        <i className="activity-item" key={`active-${i}`} onClick={() => { toIndex(i) }}>
                            <i className="activity-bg" style={{ animation: i === currentIndex ? `bg-animation ${delay / 1000}s cubic-bezier(0.34, 0.32, 0.72, 0.74) forwards` : 'none' }}></i>
                        </i>
                    ))
                }
            </div>
        </div>
    )
};

export default Carousel;
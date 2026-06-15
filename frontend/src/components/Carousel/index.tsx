import { CSSProperties, forwardRef, ReactNode, useCallback, useEffect, useImperativeHandle, useLayoutEffect, useRef, useState } from 'react';
import { flushSync } from 'react-dom';
import styles from './index.module.css';
import { CarouselProps } from './interface';

let carouselKey = 0;
const Carousel = (props: CarouselProps, ref: any) => {
    const { autoplay = false, showSwitch = false, speed = 300, dots = true, children, onChangeCurrent, className } = props;
    const carouselRef = useRef<any>(null);
    const timeRef = useRef<any>(null);
    const remainTime = useRef<number>(0);
    const startTime = useRef<number>(Date.now());
    const [isCarouselAnimate, setIsCarouselAnimate] = useState(true);
    const [width, setWidth] = useState<number>(0);
    const [currentIndex, setCurrentIndex] = useState<number>(0);
    const delay = typeof autoplay === 'number' ? autoplay : 0;
    const _children = (children && Array.isArray(children)) ? children : [];
    const _childrenLength = _children.length;

    /**
     * 无缝轮播构造的内容数组
     * @returns Array<ReactNode>
     */
    const _childrenTemp = (): Array<ReactNode> => {
        if (_children.length > 2) {
            return [_children[_children.length - 1], ..._children, _children[0]];
        } else {
            return _children;
        }
    }

    /**
     * 轮播组件内容区动画
     * @returns {object} React.CSSProperties
     */
    const carouselAnimate = (): CSSProperties => {
        return isCarouselAnimate ? {
            transitionProperty: 'transform',
            transitionDuration: `${speed / 1000}s`,
            transitionTimingFunction: `cubic- bezier(.4, 0, 1, 1)`
        } : {};
    };

    /**
     * 轮播组件内容区样式
     * @returns {object} React.CSSProperties
     */
    const carouselStyle = (): CSSProperties => {
        const len = _childrenTemp().length;
        return {
            transform: `translateX(${-(currentIndex + 1) * width}px)`, width: (len * width) + 'px',
        };
    };

    /**
     * 指示点动画
     * @param {number} index
     * @returns {object} React.CSSProperties
     */
    const dotsAnimate = (index: number): CSSProperties => {
        return index === currentIndex ? {
            animationName: `${styles['bg-animation']}`,
            animationDuration: `${delay / 1000}s`,
            animationTimingFunction: `cubic-bezier(0.34, 0.32, 0.72, 0.74)`,
            animationFillMode: 'forwards',
            animationPlayState: 'running'
        } : {}
    };

    /**
     * 切换页面
     * @param {'prev' | 'next'} action 
     */
    const prevOrNext = useCallback((action: 'prev' | 'next') => {
        let index = currentIndex;
        switch (action) {
            case 'next':
                index++;
                if (currentIndex >= _childrenLength - 1) {
                    // 立即更新DOM
                    flushSync(() => {
                        setCurrentIndex(index);
                    });
                    // 无缝轮播的边界处理
                    let time = setTimeout(() => {
                        setIsCarouselAnimate(false);
                        index = 0;
                        setCurrentIndex(index);
                        clearTimeout(time);
                    }, speed);
                } else {
                    setIsCarouselAnimate(true);
                    setCurrentIndex(index);
                }
                break;
            case 'prev':
                index--;
                if (currentIndex <= 0) {
                    flushSync(() => {
                        setCurrentIndex(index);
                    });
                    let time = setTimeout(() => {
                        setIsCarouselAnimate(false);
                        index = _childrenLength - 1;
                        setCurrentIndex(index);
                        clearTimeout(time);
                    }, speed);
                } else {
                    setIsCarouselAnimate(true);
                    setCurrentIndex(index);
                }
                break;

            default:
                break;
        }
    }, [_childrenLength, currentIndex, speed])

    useImperativeHandle(ref, () => ({
        next: () => prevOrNext('next'),
        prev: () => prevOrNext('prev'),
    }));

    /** 
     * 初始化数据
     */
    useLayoutEffect(() => {
        carouselKey++;
        setWidth(parseInt(getComputedStyle(carouselRef.current).getPropertyValue('width')) || 0);
        if (window.ResizeObserverSize) {
            const resizeObserver = new ResizeObserver((entries) => {
                for (const entry of entries) {
                    setWidth(entry.contentRect.width);
                }
            });
            carouselRef.current && resizeObserver.observe(carouselRef.current);
        }
    }, [])

    /**
     * 开始轮播
     */
    const startAutoPlay = useCallback(() => {
        if (delay > 0) {
            startTime.current = Date.now();
            timeRef.current = setTimeout(() => {
                // 在切换下一个时清空自动轮播剩余时间
                remainTime.current = 0;
                prevOrNext('next');
            }, remainTime.current > 0 ? remainTime.current : delay);
        }
    }, [delay, prevOrNext])

    /**
     * 自动轮播
     */
    useEffect(() => {
        if (delay > 0) {
            startAutoPlay();
        }
        return () => {
            clearTimeout(timeRef.current);
        }
    }, [autoplay, delay, prevOrNext, startAutoPlay])

    /**
     * 切换轮播当前页的回调
     */
    useEffect(() => {
        let index = (currentIndex > _childrenLength - 1) ? 0 : ((currentIndex < 0) ? _childrenLength - 1 : currentIndex);
        typeof onChangeCurrent === 'function' && onChangeCurrent(index);
    }, [_childrenLength, currentIndex, onChangeCurrent])


    return (
        <div className={`${styles.container} ${className}`} ref={carouselRef}>
            {/* 轮播内容区域 */}
            <div className={`${styles['carousel-list']}`} style={{ ...carouselStyle(), ...carouselAnimate() }} data-testid='carousel'>
                {
                    _childrenTemp().map((item: any, index: number) => {
                        return (
                            <div className={styles['carousel-item']} key={'carousel' + carouselKey + '' + index} style={{ width: `${width}px` }}>
                                {item}
                            </div>
                        )
                    })
                }
            </div>

            {/* 切换按钮 */}
            {
                showSwitch && (
                    <>
                        <i className={`${styles.switch} ${styles.prev}`} onClick={() => prevOrNext('prev')} title="上一页">{'<'}</i>
                        <i className={`${styles.switch} ${styles.next}`} onClick={() => prevOrNext('next')} title="下一页">{'>'}</i>
                    </>
                )
            }

            {/* 指示点 */}
            {
                dots && _childrenLength > 0 && (
                    <div className={styles.activity}>
                        {
                            _children.map((item: any, i: number) => (
                                <i className={styles['activity-item']} key={`active-${i}`} onClick={() => { setCurrentIndex(i) }}>
                                    <i className={styles['activity-bg']} style={{ ...dotsAnimate(i) }}></i>
                                </i>
                            ))
                        }
                    </div>
                )
            }
        </div>
    )
};

export default forwardRef(Carousel);
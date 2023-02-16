import React, { useEffect, useRef, useState } from 'react';
import Indicator from '../Indicator';
import './index.scss';
import { IndicatorPositionProps } from './interface';
import { IndicatorProps } from '../Indicator/interface';

interface Props {
    /**
     * 样式类名前缀
     */
    prefixClass?: string,
    /**
     * 组件
     */
    components: React.ReactNode[],
    /**
     * 延时单位毫秒
     */
    delay?: number,
    /**
     * 是否显示指示器
     */
    showIndicator?: boolean,

    /**
     * 指示器位置
     */
    indicatorPosition?: IndicatorPositionProps,
    /**
     * 方向
     * */
    orientation?: 'vertical' | 'horizontal'

    /**
     * 是否自动滚动
     * */
    auto?: boolean
    /**
     * 开始页面
     * */
    startIndex?: number

    /**
     * 指示器属性
     * */
    indicatorProps?: IndicatorProps

}

/**
 * 指示器配置
 * */
type IndicatorConfig = {
    /**
     * 当前位置
     * */
    currentIndex: number,
    /**
     * 当前位置进度
     * */
    progress: number,


}
const Carousel: React.FC<Props> = (props) => {
    const {
        delay = 3000,
        components = [],
        showIndicator = true,
        prefixClass = '',
        orientation = 'horizontal',
        auto = true,
        startIndex = 0,
        indicatorProps
    } = props;
    const [indicatorConfig, setIndicatorConfig] = useState<IndicatorConfig>({
        currentIndex: startIndex,
        progress: 0
    });
    ///开始计时时间
    const startCountDownTime = useRef<number>(Date.now());
    ///当前计时器值
    const currentRequestAnimationFrame = useRef<number>();
    //前缀
    const itemPrefix = 'carouselItem';

    const scrollPage = (currentIndex: number) => {
        const carousel = document.getElementById('carousel');
        const item = document.getElementById(`${itemPrefix}${currentIndex}`);
        if (orientation === 'horizontal') {
            window?.scrollTo({ left: 0 });
            carousel?.style.setProperty('transform', `translateX(${-(item?.offsetLeft || 0)}px)`);
        } else {
            window?.scrollTo({ top: 0 });
            carousel?.style.setProperty('transform', `translateY(${-(item?.offsetTop || 0)}px)`);
        }
        startCountDown();
    };

    const startCountDown = () => {
        if (!auto) return;
        if (currentRequestAnimationFrame.current) {
            window.cancelAnimationFrame(currentRequestAnimationFrame.current);
        }
        startCountDownTime.current = Date.now();
        countDown();
    };

    useEffect(() => {
        scrollPage(indicatorConfig.currentIndex);
    }, [indicatorConfig.currentIndex]);

    const countDown = () => {
        currentRequestAnimationFrame.current = window.requestAnimationFrame(countDown);
        const diff = Date.now() - startCountDownTime.current;
        const currentProgress: number = Math.floor((diff % delay) * 100 / delay);
        if (diff >= delay) {
            const currentIndex = (indicatorConfig.currentIndex + 1) % components.length;
            setIndicatorConfig({
                currentIndex,
                progress: currentProgress
            });
        } else {
            setIndicatorConfig({
                currentIndex: indicatorConfig.currentIndex,
                progress: currentProgress
            });
        }
    };

    const initCarouselWidth = () => {
        const carousel = document.getElementById('carousel');
        carousel?.style.setProperty('transition', 'transform 0.3s linear');
        let width = 0;
        let height = 0;
        for (let i = 0; i < components.length; i++) {
            const item = document.getElementById(`${itemPrefix}${i}`);
            width += item?.clientWidth || 0;
            height += item?.clientHeight || 0;
        }
        if (orientation === 'horizontal') {
            carousel?.style.setProperty('width', `${width}px`);
        } else {
            carousel?.style.setProperty('height', `${height}px`);
        }

        carousel?.addEventListener('scroll', (e) => {
            console.log(e.target);
        });
    };


    useEffect(() => {
        initCarouselWidth();
        startCountDown();
    }, []);

    const renderComponents = () => {
        let array = [];
        array = components.map((item, index) => {
            return <div className={'carousel-item'} key={`${itemPrefix}${index}`} id={`${itemPrefix}${index}`}>
                {item}
            </div>;
        });
        // array.push(array[0]);
        return array;
    };
    return (
        <div className={`${prefixClass} carousel-container`}>
            <div id={'carousel'} className={`carousel ${orientation === 'horizontal' ? 'fx-row' : 'fx-column'}`}>
                {components && components.length > 0 && renderComponents()}
            </div>
            {
                showIndicator &&
                <div className={`carousel-indicator-container ${orientation}`}>
                    <Indicator
                        {...indicatorProps}
                        orientation={orientation}
                        progress={indicatorConfig.progress}
                        className={prefixClass}
                        activeStyle={{ background: 'white' }}
                        currentIndex={indicatorConfig.currentIndex}
                        onClick={(currentIndex) => {
                            setIndicatorConfig({
                                currentIndex,
                                progress: 0
                            });
                        }}
                        count={components.length}/>
                </div>
            }
        </div>

    );
};

export default Carousel;

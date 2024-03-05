import React, { useState, useRef,  useEffect, ReactElement } from 'react';
import './index.css'

interface CarouselProps {
    /** 走马灯wrap 样式 */
    className?: string;
    /** 走马灯的宽度 */
    width?: number;
    /** 走马灯的高度 */
    hight?: number;
    /** 初始状走马灯的索引，从 0 开始 */
    initialIndex?: number;
    /** 自动切换的时间间隔，单位为毫秒*/
    interval?: number;	
    /** 指示条 宽度 */
    indicationWidth?: number;
    /** 指示条 高度 */
    indicationHeight?: number;
    /**
     * 切换时触发
     * @param oldIndex 原幻灯片的索引
     * @param newIndex 目前激活的幻灯片的索引
     */
    onChange?: (oldIndex: number, newIndex: number) => void;
    /** 走马灯内容 */
    children: React.ReactElement[];
}

const Carousel: React.FC<CarouselProps> = (props) => {
    const {
        children, 
        className, 
        width = window.innerWidth, 
        hight = window.innerHeight,
        initialIndex = 0, 
        interval = 3000,
        indicationWidth = 50,
        indicationHeight = 6,
        onChange
    } = props;

    // 当前指示位置
    let [currentIndex, setCurrentIndex] = useState(initialIndex);
    const curIndex = useRef<number>(initialIndex || 0);
    // 自动播放定时器
    const timer = useRef<NodeJS.Timer | null>(null);
    const childrenLength = children.length;

    /** 下一张 */
    const handleNext = () => {
        if (currentIndex === childrenLength-1 || curIndex.current === childrenLength-1) {
            setCurrentIndex(() => 0);     // 重新播放第一张
            onChange?.(curIndex.current, 0);
            curIndex.current = 0;
        } else {
            curIndex.current++;
            setCurrentIndex((oldV)=> {
                onChange?.(oldV, oldV + 1);
                return oldV + 1
            });
        }
    }

    /** 点击指示条 定位到当前位置 */
    const handleIndicationClick = (e: React.MouseEvent<HTMLDivElement, MouseEvent>, index: number)=> {
        curIndex.current = index;
        setCurrentIndex(()=> index);
        timer.current && clearInterval(timer.current);
    }

    // 遍历生成新的item组件集合
    const child = React.Children.map(children, (item, index) => {
        // 校验传入的子组件是不是CarouselItem
        if(React.isValidElement(item) && (item as any).type.type.displayName === 'CarouselItem') {
            const childProps = { 
                ...(item as ReactElement).props, 
                width, 
                index
            };
            // 向每一个子组件传参 
            return React.cloneElement(item, childProps);
        }else{
            console.warn("传入的子组件非CarouselItem组件，需传入CarouselItem组件")
        }
    });
   
    useEffect(()=>{
        // 自动播放
        timer.current = setInterval(handleNext, 3000);
        return ()=>{
            timer.current && clearInterval(timer.current);
        }
    }, [handleIndicationClick])

    return (
        <div className={`carousel-wrap ${className}`} 
            style={{ width: `${width}px`, height: `${hight}px`}}>
            <div className='carousel-container-wrap'
            style={{ 
                transition: `${interval/3}ms`, 
                left: `-${currentIndex * width}px`,  
                width: `${width * childrenLength}px`}}
            >
               {child}
            </div>
            <div className="indication-wrap">
                {Object.keys(children)?.map((i, index)=> {
                    return (
                    <div className={`indication-item-wrap`} 
                    key={index} 
                    onClick={(e)=> handleIndicationClick(e, index)} 
                    style={{
                        width: `${indicationWidth}px`, 
                        height: `${indicationHeight}px`}}
                    >
                        <div className='indication-item' 
                        style={{
                            transition: currentIndex === index ? `${interval}ms` : '0s', 
                            width: currentIndex === index ? `${indicationWidth}px`: 0, 
                            height: `${indicationHeight}px`
                        }}></div>
                    </div>
                    )
                })}
            </div>
        </div>
    )
}

export default Carousel;
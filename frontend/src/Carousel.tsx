import React, { useEffect, useMemo, useState } from "react";
import './Carousel.css';

function Carousel(props: { children: JSX.Element[], width: number, height: number, onChange: (index: number) => void }) {
    const { children, width, height, onChange } = props

    const [currentIndex, setIndex] = useState(0)

    // 无线循环滚动
    useEffect(() => {
        const timer = setTimeout(() => {
            let nextIndex = (currentIndex + 1) % children.length
            requestAnimationFrame(() => {
                setIndex(nextIndex)
                // 告知外部当前显示的是第几页
                onChange && onChange(nextIndex)
            })
        }, 3000)
        return () => { clearTimeout(timer) }
    }, [currentIndex])

    // 通过控制偏移量达到轮播效果
    const offset = useMemo(() => {
        return -currentIndex * width;
    }, [currentIndex])

    function renderChildren() {
        const childStyle = {
            width: width,
            height: height
        };

        return React.Children.map(children, child => {
            const childClone = React.cloneElement(child, { style: childStyle });
            return (
                <div style={{ display: 'inline-block' }}>{childClone}</div>
            );
        });
    }

    return (
        <div className="frame" style={{ marginLeft: offset }}>{renderChildren()}</div>
    )
}

export default Carousel;
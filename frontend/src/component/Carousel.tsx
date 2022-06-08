import React, { useState } from 'react';
// import ReactDOM from 'react-dom';
import './Carousel.css';
interface iProps {
    children: React.ReactNode
}
interface iItem {
    id: number,//唯一ID
    html: JSX.Element,//html 内容
    isShow: boolean,
    isEnd: boolean
}
export default function Carousel(prop: iProps) {
    let btns: JSX.Element[] = [];//每页的下标按钮
    let htmls: JSX.Element[] = [];//每页的内容
    let newItems: iItem[] = []
    React.Children.forEach(prop.children, (child, index) => {
        let item = {
            id: index,
            html: child as JSX.Element,
            isShow: (index === 0) ? true : false,
            isEnd: false
        };
        newItems.push(item);

    })
    const [items, setItems] = useState<iItem[]>(newItems); //滚动的子项目
    /**
     * 播放动画的下标
     *
     * @param {number} index 
     */
    const animationEnd = (index: number): void => {
        index = (index) % items.length;
        if (items[index].isShow) {
            return
        }
        let newItems = [...items];
        for (let i = 0; i < newItems.length; i++) {
            let item = newItems[i];
            item.isEnd = false;
            if (i === index) {
                item.isShow = true
            } else {
                if (item.isShow) {
                    item.isEnd = true;
                }
                item.isShow = false
            }
        }
        setItems(newItems);
    }
    /**
     * 下标按钮className
     *
     * @param {number} index
     * @returns {string}
     */
    let btnClassNames = (index: number): string => {
        let item = items[index]
        if (item.isShow) {
            return "carousel-btn animation"
        } else {
            return "carousel-btn"
        }
    }
    /**
     * 内容容器className
     *
     * @param {number} index
     * @returns {string}
     */
    let htmlClassNames = (index: number): string => {
        let item = items[index]
        let arr = ["carousel-content-item"];
        if (item.isEnd) {
            arr.push("animation-left-end")
        } else if (item.isShow) {
            arr.push("animation-left-start")
        }
        return arr.join(" ")
    };
    //生成 每页的下标按钮和内容   
    for (let index = 0; index < items.length; index++) {
        let item = items[index];
        btns.push(<span key={item.id} className={btnClassNames(index)} onMouseEnter={() => { animationEnd(index) }} >
            <span onAnimationEnd={() => animationEnd(index + 1)}></span>
        </span>);
        //添加内容部分
        htmls.push(<div key={item.id} className={htmlClassNames(index)}>{item.html}</div>)
    }
    return (<div className='page'>
        <div className='page-content'>
            {htmls}
        </div>
        <div className='carousel'>
            {btns}
        </div>
    </div>);
}
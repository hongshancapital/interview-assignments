import React, { useState } from 'react';
// import ReactDOM from 'react-dom';
import './Carousel.css';
interface iProps {
    children: React.ReactNode
}
interface iPages {
    id: number,//唯一ID
    content: JSX.Element,//content 内容
    isShow: boolean,
    isEnd: boolean
}
export default function Carousel(prop: iProps) {
    const pagation: JSX.Element[] = [];//每页的下标按钮
    const contents: JSX.Element[] = [];//每页的内容
    const pages: iPages[] = []
    React.Children.forEach(prop.children, (child, index) => {
        const item = {
            id: index,
            content: child as JSX.Element,
            isShow: (index === 0) ? true : false,
            isEnd: false
        };
        pages.push(item);

    })
    const [items, setItems] = useState<iPages[]>(pages); //滚动的子项目
    /**
     * 播放动画的下标
     *
     * @param {number} index
     */
    const ainimation = (index: number): void => {
        index = (index) % items.length;
        if (items[index].isShow) {
            return
        }
        let pages = Array.from(items)
        pages.forEach((item,i)=>{
            item.isEnd = false;
            item.isShow = i === index ? true  : item.isShow
            if (i !== index) {
                item.isEnd = item.isShow ? true : item.isShow
                item.isShow = false
            }
        })
        setItems(pages);
    }


    items.forEach( (item,i)=>{
        let clsAnimation = item.isShow ? "carousel-btn animation" :  "carousel-btn"
        let cls = "carousel-content-item"
        if (item.isEnd) {
            cls += " animation-left-end"
        } else if (item.isShow) {
            cls += " animation-left-start"
        }

        contents.push(<div key={item.id} className={cls}>{item.content}</div>)
        pagation.push(<div key={item.id} className={clsAnimation} onClick={() => { ainimation(i) }} >
        <span onAnimationEnd={() => ainimation(i + 1)}></span>
            </div>);
    })

    return (<div className='page'>
        <div className='content'>
            {contents}
        </div>
        <div className='carousel'>
            {pagation}
        </div>
    </div>);
}

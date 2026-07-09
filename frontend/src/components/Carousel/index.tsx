import React, { useState, useEffect } from 'react';
// import {TimerBar as TimerBarV2} from '../TimerBarV2'
import './index.scss'

interface IdataItem {
    img: string;
    desc: JSX.Element;
}
interface ICarousel {
  // 轮播图数据（必传）
  items: IdataItem[];
}

const Carousel = (props: ICarousel) => {
  const { items } = props;
  const [translatex, setTranslatex] = useState('0')

  // 轮播下一个图片
  const nextImage = (index: number) => {
    setTranslatex(`${index * -100}vw`)
  }

  const [curIndex, setCurIndex] = useState([0]);
  // 定时器setInterval, 定时改变图片index
  useEffect(() => {
    const interval = setInterval(() => {
      const nextIndex = curIndex[0] >= items.length - 1 ? 0 : curIndex[0] + 1;
      curIndex[0] = nextIndex;
      setCurIndex([nextIndex]);
    }, 3000);
    return () => {
      // 组件销毁时 清空定时器
      clearInterval(interval);
    };
  }, []);

  // 当前index改变时 触发翻页
  useEffect(() => {
    nextImage(curIndex[0]);
  }, [curIndex]);

  return (<div className='CarouselBox' >
    <div className='imgboxs' style={{transform: `translatex(${translatex})`}}>
        {items.map(item => {
            return  <div className={`imgbox ${item.img}`} key={item.img}>
                <div className={`img ${item.img}`}>
                    {item.desc}
                </div>
            </div>
        })}
    </div>
    {/*<TimerBarV2 className='tb' num={items.length} onTime={nextImage}/>*/}
  </div>)
}

export { Carousel };

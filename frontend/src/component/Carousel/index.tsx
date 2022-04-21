import React from 'react';
import { useItemIndex} from './itemProps';
import './index.scss';

export interface DataProps {
  list: {
    title: string[];
    text?: string[];
    class: string;
    url?:string;
  }[];
  // 轮播间隔 单位毫秒
  time?: number;
  // 面板指示点
  dotsFlag?:boolean;
}

const Carousel=(props:DataProps)=> {
  const { time=3000 , list = [], dotsFlag=true} = props;
  let len = list.length;
  const { currentIndex,setCurrentIndex } = useItemIndex({
    count: len,
    time:time,
  });
  return <div className='carousel-wrap' >
    {list.map((v,idx) =>(
    <div className={`carousel-item ${idx === currentIndex ? 'active' : ''}`} key={`carousel_${idx}`} style={{ transform: `translate3d(-${(currentIndex) * 100}%,0,0)` }}>
      <div className='item-wrapper'>
        <div className='title'>
            {v.title.map((v, idx) => (
                <div key={`title_${idx}`}>{v}</div>
            ))}
        </div>
        {v.text && <div className='text'>
            {v.text.map((v, idx) => (
                <div key={`text_${idx}`}>{v}</div>
            ))}
        </div>}
      </div>

      <div className={'img ' + v.class}></div>
    </div>))}
    {dotsFlag&&<div className='dots-box'>
      {Array(len).fill('a').map((v, idx) => (
        <div key={`progress_${idx}`} className='dots-item' onClick={()=>{setCurrentIndex(idx++)}}>
          <div className={`progress ${idx === currentIndex ? 'active-pro':''}`}></div>
      </div>
      ))}
    </div>}
  </div>
}
export default Carousel;
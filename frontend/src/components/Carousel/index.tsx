import React, { useEffect,useState,ReactElement } from 'react';
import './index.scss';

export interface DataProps {
  list: {
    title: string | ReactElement;
    text?: string | ReactElement;
    class: string;
    url?:string;
  }[];
  // 轮播间隔 单位毫秒
  time?: number;
}
const Carousel=(props:DataProps)=> {
  const { time=3000 , list = [] } = props;
  //当前选中的
  let [index, setIndex] = useState(0);
  let len = list.length;
  useEffect(() => {
    const timer = setInterval(() => {
      setIndex(++index % len)
    }, time)
    return () => clearInterval(timer);
  }, [len, time, index])
  return <div className='carousel-wrap' >
    {list.map((v,idx) =>(
    <div className={`carousel-item ${idx === index ? 'active' : ''}`} key={v.url} style={{ transform: `translate3d(-${(index) * 100}%,0,0)` }}>
      <div className='item-wrapper'>
        <div className='title'>{v.title}</div>
        {v.text && <div className='text'>{v.text}</div>}
      </div>

      <div className={'img ' + v.class}></div>
      {/* <img src={v.url} className='img' /> */}
    </div>))}
    <div className='dots-box'>
      {Array(len).fill('a').map((v, idx) => (
        <div key={v+idx} className='dots-item' onClick={()=>{setIndex(idx++ % len)}}>
          <div className={`progress ${idx === index ? 'active-pro':''}`}></div>
      </div>
      ))}
    </div>
  </div>
}
export default Carousel;
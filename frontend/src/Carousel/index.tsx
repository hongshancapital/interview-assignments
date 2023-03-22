import React, { useEffect, useState } from "react";
import "./index.css";

interface CarouselProps {
  // 轮播图数据列表
  list?:{url:string,width:number}[]
  // 轮播图滚动时间，默认2s
  intervalTime?:number;
}
const Carousel:React.FC<CarouselProps> = ({list=[],intervalTime=2000}) => {
  const [listCopy,setListCopy] = useState(list)
  const [count, setCount] = useState(0);
  // 监听轮播图
  useEffect(() => {
    const timer = setInterval(() => {
      if (count >= listCopy?.length-1) {
        setCount(0);
      } else {
        setCount(count + 1);
      }
    }, intervalTime);
    return () => clearInterval(timer)
  }, [count]);
  // 监听轮播图下方进度条
  useEffect(()=>{
    const tempList = [...listCopy];
    if(count===list.length-1){
      tempList.forEach(item=>item.width=0)
    }
    tempList[count].width=30;
    setListCopy(tempList)
  },[count])
  return (
    <div className="box">
      <ul className="imgs" style={{ marginLeft: -count * 375 }}>
        {listCopy.map((el) => {
          return (
            <li className="item" key={el?.url}>
              <img src={el?.url} /> 
            </li>
          );
        })}
      </ul>
      <div className="lines">
        {
          listCopy.map((item,index)=> 
            <div key={item?.url} className="line-container">
              <div className={index===count?'line':''} style={{width:item?.width}}>
              </div>
            </div>)
        }
      </div>
    
    </div>
  );
};

export default Carousel;


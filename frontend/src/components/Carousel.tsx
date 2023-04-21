import './carousel.scss';
import { useState, useRef } from 'react'
interface dataType {
    name: string;
    img: string;
}

interface dataTypes{
    [index: number]: dataType;
}

const Vivew = function(props:any) {

    //获取dom
    const boxItemRef = useRef<any>({});

    

    // 获取传入参数
    let { img,delay } = props || [];

    //设置数据
    
    let [offsetNum, setOffsetNum] = useState(200);
    let [index,setIndex] = useState(0);
    let [timer,setTimer] = useState<any>(0);
    
    // 定时设置样式
    timer = setInterval(() => {
        if(timer) {
            clearInterval(timer);
        }
        setTimer(timer);
        boxItemRef.current.style.transform = `translateX(${offsetNum * -1}px)`;
        setOffsetNum(offsetNum+200);
        setIndex(index+1);
        if(index >= img.length - 1) {
            boxItemRef.current.style.transform = `translateX(0)`;
            boxItemRef.current.style.transition = 'transform(-10s)';
            setIndex(0);
            setOffsetNum(200);
        }
    },delay)
    
    
    return <div className='box'>
        <div className='box-item' ref={boxItemRef}>
            {
                img.map((item:any,index:number) => {
                    return <div key={item.name}  className={`ca-item ca-item-${index}`}>{item.name}</div>
                })
            }
            
        </div>
    </div>
}

export default Vivew;
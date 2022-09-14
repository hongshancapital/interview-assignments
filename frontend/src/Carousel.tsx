import React, { useState, useEffect } from 'react';
import "./Carousel.css";

interface IProps {
    len: number
}
 const Carousel:React.FC<IProps> = (props) => {
    let [cur, setcur] = useState<number>(0)
    const { len } = props;
    const dscroll = (les: number) => {
        setTimeout(()=>{
            setcur(les);
        },3500);
    }

    useEffect(()=>{
        cur+=100;
        if(cur < len * 100){
            dscroll(cur);
        }else{
            cur = 0;
            dscroll(cur);
        }
    },[cur])    
    // 下面样式都可当组件 循环输出
    return <><div className='win'>
             <ul className='cwrap' style={{width: 100* len + 'vw', transform:`translateX(-${cur}vw)` }}>
                    <li className="c1"></li>
                    <li className="c2"></li>
                    <li className="c3"></li>
             </ul>
            <div className='linebox'>
                <div className='lineboxwrap'><div className={cur === 0 ? 'line': ''}></div></div>
                <div className='lineboxwrap'><div className={cur === 100 ? 'line': ''}></div></div>
                <div className='lineboxwrap'><div className={cur === 200 ? 'line': ''}></div></div>
            </div>
    </div>
    </>
}


export default Carousel;
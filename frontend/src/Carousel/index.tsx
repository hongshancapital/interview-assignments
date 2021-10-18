import React, { useState, useRef, useEffect, CSSProperties } from 'react';
import "./index.css";

interface Props {
    children: Array<object>
}

let nowIndex: number = 0;
let length: number = 0;

function Carousel(props: Props) {

    const [style, setStyle] = useState<CSSProperties>({transform: 'translate(0)'});
    const myRef = useRef<number>(0);
    
    useEffect(() => {
        length = props.children.length;
        openCarousel();
    }, [])

    const openCarousel = () => {
        setInterval(() => {
            // 控制下面指示条
            if(myRef.current === (length-1)) {
                myRef.current = 0;
            } else {
                myRef.current++;
            }
            changeNext();
        }, 3000);
    }

    // 控制移动帧
    const changeNext = () => {
        let i: number = -100 * nowIndex;
        const timer = setInterval(() => {
            // 判断是否滚动完一页
            if(i === -100*(nowIndex+1)) {
                clearInterval(timer);
                nowIndex++;
                if(nowIndex === length) {
                    setStyle({transform: `translate(0)`});
                    nowIndex = 0;
                }
            } else {
                i = i - 4;
                setStyle({transform: `translate(${i}vw)`});
            }   
        },50) 
    }

    return ( 
        <div className="carousel"> 
            <div className="container" style={style}>
                {
                    props.children.map((value: object, index: number) => (
                        <div className="item" key={index}>
                            { value }
                        </div>   
                    ))
                }
                <div className="item">
                    { props.children[0] }
                </div> 
                
            </div>
            <div className="line">
                {
                    props.children.map(( value: object, index: number) => (
                        <div className="line-item" key={index}>
                            <p className="line-item-top" />
                            <p className="line-item-bottom" id={myRef.current===index? "animate" : ""}/>
                        </div>
                    ))
                }
            </div>
        </div> 
    ); 
}


 export default Carousel
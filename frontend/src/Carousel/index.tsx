import { type } from 'os';
import React, { useState, useRef, useEffect, CSSProperties } from 'react';
import "./index.css";

interface Props {
    data: Array<{
        backgroundImg: string,
        backgroundColor: string,
        title: string,
        describe?: string,
        fontColor: string,
      }>
}


let nowIndex: number = 0;
let length: number = 0;

function Carousel(props: Props) {

    const [style, setStyle] = useState<CSSProperties>({transform: 'translate(0)'});
    const myRef = useRef<number>(0);
    
    useEffect(() => {
        length = props.data.length;
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
        },10) 
    }

    return ( 
        <div className="carousel"> 
            <div className="container" style={style}>
                {
                    props.data.map((value, index) => (
                        <div className="item" key={index} style={{backgroundColor: value.backgroundColor}}>    
                            <div className="item-title" style={{color: value.fontColor}}>
                                { value.title }
                            </div>
                            <div className="item-describe" style={{color: value.fontColor}}>
                                { value.describe }
                            </div>
                            <img className="item-img" src={value.backgroundImg} />
                        </div>   
                    ))
                }
                <div className="item" style={{backgroundColor: props.data[0].backgroundColor}}>    
                    <div className="item-title" style={{color: props.data[0].fontColor}}>
                        { props.data[0].title }
                    </div>
                    <div className="item-describe" style={{color: props.data[0].fontColor}}>
                        { props.data[0].describe }
                    </div>
                    <img className="item-img" src={props.data[0].backgroundImg} />
                </div> 
                
            </div>
            <div className="line">
                {
                    props.data.map(( value, index) => (
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
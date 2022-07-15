import React, { useState, useEffect } from "react";
import Header from "./Header";
import "./carousel.scss";

function Carousel(){
    const showTime:number = 3000;
    let [toggle, setToggle] = useState(0);
    let left:string = '-' + ((toggle % 3) * 100) + 'vw';
    let [leftProgress, setLeft] = useState({});
    let [middleProgress, setMiddle] = useState({});
    let [rightProgress, setRight] = useState({});
    useEffect(() => {
        setLeft({
            transition: 'width, linear, 3s',
            width: '100%'
        })
    
    }, []);
    useEffect(() => {
        let timerToggle: ReturnType<typeof setTimeout> = setInterval(changeNext, showTime);
        function changeNext():void {
            setToggle((toggle) => {
                setLeft(() => {
                    if(toggle % 3 === 2){
                        return {
                            transition: 'width, linear, 3s',
                            width: '100%'
                        };
                    }else if(toggle % 3 === 0){
                        return {
                            width: '0'
                        };
                    }
                })
                setMiddle(() => {
                    if(toggle % 3 === 0){
                        return {
                            transition: 'width, linear, 3s',
                            width: '100%'
                        };
                    }else if(toggle % 3 === 1){
                        return {
                            width: '0'
                        }
                    }
                })
                setRight(() => {
                    if(toggle % 3 === 1){
                        return {
                            transition: 'width, linear, 3s',
                            width: '100%'
                        };
                    }else if(toggle % 3 === 2){
                        return {
                            width: '0'
                        }
                    }
                })
                return toggle + 1;
            });
        }
        return () => {
            clearInterval(timerToggle);
        }
    })
    return (
        <div className="carousel">
            <div className="content" style={{left}}>
                <div className="left">
                    <div className="text">
                        <h1>xPhone</h1>
                        <div>Lots to love. Less to spend.</div>
                        <div>Starting at $399.</div>
                    </div>
                    <Header />
                    <div className="left-img img">
                    </div>
                </div>
                <div className="middle">
                    <div className="text">
                        <h1>Tablet</h1>
                        <div>Just the right amount of everything.</div>
                    </div>
                    <Header />
                    <div className="middle-img img">
                    </div>
                </div>
                <div className="right">
                    <div className="text">
                        <h1>Buy a Tablet or xPhone for college.</h1>
                        <h1>Get arPods</h1>
                    </div>
                    <Header />
                    <div className="right-img img">
                    </div>
                </div>
            </div>
            <div className="progress">
                <div className="margin-box">
                    <div className="flex"><div style={leftProgress}></div></div>
                    <div className="flex"><div style={middleProgress}></div></div>
                    <div className="flex"><div style={rightProgress}></div></div>
                </div>
            </div>
        </div>
    );
}

export default Carousel;
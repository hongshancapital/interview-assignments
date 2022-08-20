import React, { useState, useEffect } from 'react';

import img1 from '../../assets/iphone.png';
import img2 from '../../assets/tablet.png';
import img3 from '../../assets/airpods.png';
import './index.css';

function Carousel() {
    const [index, setIndex] = useState(0)
    const [flag, setFlag] = useState(false)

    useEffect(() => {
        setFlag(true)
        setInterval(() => {
            setIndex(index => index + 1)
        }, 3000)
    }, [])

    useEffect(() => {
        setIndex(index == 3 ? 0 : index)
    }, [index])

    const renderPoint = () => {
        return <div className='pointBox'>
            {
                [1, 2, 3].map((item, ind) => {
                    return <div className='point' key={item}>
                        <div className='pointInner' style={{ width: flag && ind == index ? 80 : 0, transition: ind == index ? 'width 3s linear' : 'none' }}></div>
                    </div>
                })
            }
        </div>

    }

    return <div className="Carousel">
        <div className='box' style={{ left: -index * 1000 }}>
            <div className='content'>
                <div className='title'>
                    <div className='titleName'>xPhone</div>
                    <div>Lots to love.Less to spend</div>
                    <div>Starting at $399.</div>
                </div>
                <img src={img1} alt="" />
            </div>
            <div className='content'>
                <div className='title titleOther'>
                    <div className='titleName'>Tablet</div>
                    <div>Just the right amount of everything.</div>
                </div>
                <img src={img2} alt="" />
            </div>
            <div className='content'>
                <div className='title titleOther'>
                    <div className='titleName'>Buy a Tablet or xPhone for college.</div>
                    <div className='titleName'>Get arPods.</div>
                </div>
                <img src={img3} alt="" />
            </div>
        </div>
        {renderPoint()}
    </div>;
}

export default Carousel;
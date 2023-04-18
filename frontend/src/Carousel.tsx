import React, { useEffect, useState } from 'react';
import { Carousel as Swiper } from 'antd';

import airpods from './assets/airpods.png'
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'



const Carousel: React.FC = () => {
    const [clientHeight, setClientHeight] = useState(0)
    const contentStyleOne: React.CSSProperties = {
        margin: 0,
        height: `${clientHeight}px`,
        color: '#fff',
        lineHeight: `${clientHeight}px`,
        textAlign: 'center',
        background: '#000000',
        position: 'relative'
    };
    const contentStyleTwo: React.CSSProperties = {
        margin: 0,
        height: `${clientHeight}px`,
        color: '#fff',
        lineHeight: `${clientHeight}px`,
        textAlign: 'center',
        background: '#ffffff',
        position: 'relative'
    };
    const contentStyleThree: React.CSSProperties = {
        margin: 0,
        height: `${clientHeight}px`,
        color: '#fff',
        lineHeight: `${clientHeight}px`,
        textAlign: 'center',
        background: '#ffffff',
        position: 'relative'
    };

    const resizeUpdate = (e: any) => {
        let height = e.target.innerHeight;
        setClientHeight(height)
    }

    useEffect(() => {
        setClientHeight(document.body.clientHeight)
        window.addEventListener('resize', resizeUpdate)
        return () => {
            window.removeEventListener('resize', resizeUpdate);
        }
    }, [])

    return (
        <Swiper autoplay>

            <div>
                <div style={contentStyleOne}>
                    <img src={iphone} alt="" style={{ width: '100%', position: 'absolute', bottom: '0' }} />
                </div>
            </div>

            <div>
                <div style={contentStyleTwo}>
                    <img src={tablet} alt="" style={{ width: '100%', position: 'absolute', bottom: '0' }} />
                </div>
            </div>

            <div>
                <div style={contentStyleThree}>
                    <img src={airpods} alt="" style={{ width: '100%', position: 'absolute', bottom: '0' }} />
                </div>
            </div>

        </Swiper>
    );
};

export default Carousel;
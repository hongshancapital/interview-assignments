import './Carousel.scss';
import React, { useState, useEffect, useRef } from "react";

function Carousel (props: any) {
    const dv = useRef<any>()
    const [imgs, setImgs]: any = useState<any>([
        {
            title: 'PhoneX',
            url: 'https://publicvp.oss-cn-beijing.aliyuncs.com/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20220531115455.png'
        },
        {
            title: 'Phone11',
            url: 'https://publicvp.oss-cn-beijing.aliyuncs.com/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20220531115455.png'
        },
        {
            title: 'Phone13',
            url: 'https://publicvp.oss-cn-beijing.aliyuncs.com/%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_20220531115455.png'
        }
    ]);
    const stp: any = useRef(0);
    const [inactive, setInactive]: any = useState(0)

    useEffect(() => {
        const oWidth = document.body.offsetWidth;
        dv.current.style.width = oWidth * imgs.length + 'px';
        let step = 0;
        setInterval(() =>{
            if (step >= imgs.length - 1) {
                step = 0;
                setInactive(step)
                dv.current.style.left = '0px';
                return
            }
            step ++;
            dv.current.style.left = -step*oWidth + 'px';
            setInactive(step)
        }, 3000)
    }, [])

    return (
        <div className="swiperBox">
            <div className="car_wrap" ref={ dv }>
                {
                    imgs.map((item: any, index: number) => {
                        return (
                            <div className="swiper_ul" key={index}>
                                <div className="font_dv">
                                    <h1>{item.title}</h1>
                                    <div>Lots to Love.Less to Send</div>
                                    <div>Staring at $399</div>
                                </div>
                                <div className="pic_dv">
                                    <img src={item.url} alt="pic"/>
                                </div>
                            </div>
                        )
                    })
                }
            </div>
            <div className="swiper_barBox">
                {
                    imgs.map((v: any, index: any) => {
                        return <span className={inactive == index ? 'swiper_progess avred' : 'swiper_progess'} key={index}></span>
                    })
                }
            </div>
        </div>
    )
}

export default Carousel;
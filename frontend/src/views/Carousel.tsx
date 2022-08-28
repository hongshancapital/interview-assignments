import React from 'react'
import { Swiper } from '../components/Swiper'
import airpods from '../assets/airpods.png'
import iphone from '../assets/iphone.png'
import tablet from '../assets/tablet.png'
import './carousel.css'


const Phone = () => {
    return <div className={'phone'}>
        <p>xPhone</p>
        <p>Lots to love. Less to spend.</p>
        <p>Starting at $399.</p>
        <img src={iphone} />
    </div>
}
const Tablet = () => {
    return <div className={'tablet'}>
        <p>Tablet</p>
        <p>Just the right amount of everything.</p>
        <img src={tablet} />
    </div>
}
const AirPods = () => {
    return <div className='airpods'>
        <p>Tablet</p>
        <p>Just the right amount of everything.</p>
        <img src={airpods} />
    </div>
}

export const Carousel = () => {
    const pages = [
        <Phone />,
        <Tablet />,
        <AirPods />
    ]
    return <Swiper contents={pages} duration={3000} />
}
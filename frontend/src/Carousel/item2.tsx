import React from 'react'
import url from '../assets/tablet.png'

import "./item2.css"

export default function CarouselItem() {

    return <div className='carouse-item carouse-item2'>
        <img src={url} alt="" key={url}></img>
        <div className='carouse-text-wrap'>
            <div className='item-title'>Tablet</div>
            <div>Just the right amount of everything.</div>
        </div>
    </div>

}
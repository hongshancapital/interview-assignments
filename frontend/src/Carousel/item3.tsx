import React from 'react'
import url from '../assets/airpods.png'

import "./item3.css"

export default function CarouselItem() {

    return <div className='carouse-item carouse-item3'>
        <img src={url} alt="" key={url}></img>
        <div className='carouse-text-wrap'>
            <div className='item-title'>Buy the Tablet or xPhone for college</div>
            <div className='item-title'>Get Airpods</div>
        </div>
    </div>

}
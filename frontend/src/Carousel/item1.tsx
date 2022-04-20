import React from 'react'
import url from '../assets/iphone.png'

import "./item1.css"

export default function CarouselItem() {

    return <div className='carouse-item carouse-item1'>
        <img src={url} alt="" key={url}></img>
        <div className='carouse-text-wrap'>
            <div className='item-title'>xPhone</div>
            <div>Lots to love.Less to spend.</div>
            <div>Starting at $399.</div>
        </div>
    </div>

}
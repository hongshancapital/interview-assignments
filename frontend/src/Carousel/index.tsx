import React from 'react';
import Tab from './tab';

function Carousel(){
    const resource = [
        {
            title: 'xphone',
            p: 'aaa',
            img: 'iphone'
        },{
            title: 'Tablet',
            p: 'Just the right',
            img: 'tablet'
        },{
            title: 'Buy a Tablet',
            p: 'sss',
            img: 'airpods'
        }
    ]
    return (
        <div>
            <Tab resource={resource} />
        </div>
    )
}

export default Carousel;
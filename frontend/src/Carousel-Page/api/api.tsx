import React from 'react'
export const getCarouselResourceList=()=>{
    
    return Promise.resolve([
        {style:{"backgroundColor": "aqua"},innerHTML:<span>please read readme.md--1</span>},
        {style:{"backgroundColor": "rgb(47, 77, 57)"},innerHTML:<span>please read readme.md--2</span>},
        {style:{"backgroundColor": "rgba(0, 0, 255, 0.387)"},innerHTML:<span>please read readme.md--3</span>},
        {style:{"backgroundColor": "rgba(255, 0, 0, 0.425)"},innerHTML:<span>please read readme.md--4</span>},
        {style:{"backgroundColor": "orange"},innerHTML:<span>please read readme.md--5</span>}
    ])
}
export const getCarouselResourceList1=()=>{
    
    return Promise.resolve([
        {style:{"backgroundColor": "rgba(0, 0, 255, 0.387)"},innerHTML:<span>please read readme.md--five</span>},
        {style:{"backgroundColor": "rgba(255, 0, 0, 0.425)"},innerHTML:<span>please read readme.md--four</span>},
        {style:{"backgroundColor": "orange"},innerHTML:<span>please read readme.md--three</span>},
        {style:{"backgroundColor": "aqua"},innerHTML:<span>please read readme.md--two</span>},
        {style:{"backgroundColor": "rgb(47, 77, 57)"},innerHTML:<span>please read readme.md--one</span>}
        
    ])
}
import React from 'react'
import { IconType } from '../types';
import '../styles/Image.css'

type Prop = {
    iconType:IconType
}
function Image({iconType}:Prop) {
    return (
        <div className={`imageWrapper ${iconType}`} />
    )
}

export default Image;

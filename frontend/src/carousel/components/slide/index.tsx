import React from 'react'
import { SlideContent } from '../../../interface/carousel-interface'
import { titleStyle, descStyle } from '../../../config/index'
import './index.css'

interface Props {
    slide: SlideContent
}

const Slide: React.FC<Props> = (props) => {
    const { slide } = props
    const {descContent, pic} = slide

    const getStyle = (type: string) => {
        let style
        if (type === 'title') {
            style = titleStyle
        }

        if (type === 'desc') {
            style = descStyle
        }

        if (slide.bgColor === 'black') {
            style = {...style, color: 'white'}
        }

        return style
    }

    return (
        <div className='slide'>
            {/* put the image as the background */}
            {pic ? <img className='slide-img' src={pic.url}></img> : null}
            <div className='slide-wrapper'>
                <div className='slide-desc'>
                    {descContent && descContent.length ? descContent.map((desc, index) => {
                        const { content, type } = desc
                        return (
                            <div key={index} style={{...getStyle(type)}}>{content}</div>
                        )
                    }) : null}
                </div>
            </div>
        </div>
    )
}

export default Slide
import React from 'react'
import { SlideContent } from '../../../interface/carousel-interface'
import './index.css'

interface Props {
    slide: SlideContent
}

const Slide: React.FC<Props> = (props) => {
    const { slide } = props
    const {descContent, pic} = slide
    /** get the style for the image */
    const getImgStyle = () => {
        if (pic) {
            return {
                width: pic.width,
                height: pic.height
            }
        }

        return {}
    }

    return (
        <div className='slide'>
            {/* put the image as the background */}
            {pic ? <img className='slide-img' src={pic.url}></img> : null}
            <div className='slide-wrapper'>
                <div className='slide-desc'>
                    {descContent && descContent.length ? descContent.map((desc, index) => {
                        const { content, style } = desc
                        console.log('content: ', content)
                        return (
                            <div key={index} style={{...style}}>{content}</div>
                        )
                    }) : null}
                </div>
            </div>
        </div>
    )
}

export default Slide
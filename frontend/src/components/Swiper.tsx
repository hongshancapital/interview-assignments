import React, { useEffect, useState } from 'react'
import './swiper.css'

interface Props {
    contents: JSX.Element[]
    duration?: number
}

export const Swiper = (props: Props) => {
    const { contents, duration } = props

    const [width, setWidth] = useState(window.innerWidth)
    useEffect(() => {
        const handleResize = () => setWidth(window.innerWidth)
        window.addEventListener('resize', handleResize)
        return () => window.removeEventListener('resize', handleResize)
    }, [])

    const [curContent, setCurContent] = useState(0)
    useEffect(() => {
        const timer = setInterval(() => {
            setCurContent(pre => pre + 1 >= contents.length ? 0 : pre + 1)
        }, duration ?? 3000)
        return () => {
            if (timer) {
                clearInterval(timer)
            }
        }
    }, [contents])

    return <div className={'swiper'}>
        <div className='swiper-container'>
            {contents.map(content =>
                <div className={'swiper-content'} 
                style={{ width:width, transform: `translate3d(${-curContent * width}px,0,0)` }}>
                    {content}
                </div>)}
        </div>
        <div className={'indicator'}>
            {contents.map((_, index) =>
                <div className='indicator-pointer'>
                    <div className='indicator-process' 
                    style={curContent === index ? { width: '40px', transition: `${duration ?? 3000}ms ease-in-out` } : {}} />
                </div>
            )}
        </div>
    </div>


}
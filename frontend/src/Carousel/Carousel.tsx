import React, { useState } from 'react';
import './Carousel.css';
interface CarouselProps {
    children: React.ReactNode
}
function Carousel({ children }: CarouselProps) {
    const [current, setCurrent] = useState<number>(0);
    const newChildren = React.Children.toArray(children);
    const handleAnimationEnd = () => {
        setCurrent(current === newChildren.length - 1 ? 0 : current + 1);
    }
    const wrapperStyle = { transform: `translateX(-${current * 100}%)` }
    return (
        <div className='carousel '>
            <div className='carousel__innerwrap' style={wrapperStyle}>
                {
                    newChildren.map((child, index) => <div className='carousel__box' key={index}>{child}</div>)
                }
            </div>
            {
                newChildren.length > 1 &&
                <div className='carousel__slider'>
                    {
                            newChildren.map((_, index) => {
                                return (
                                    <div key={`s_${index}`} className='sliderbar'>
                                        <div className={index === current ? 'active' : ''} onAnimationEnd={handleAnimationEnd}></div>
                                    </div>
                                )
                            })
                        }
                    </div>
            }
        </div>
    )
}
export default Carousel;
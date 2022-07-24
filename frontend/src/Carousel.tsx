import React, { ReactNode, useEffect, useState } from 'react'
import './Carousel.css'


interface Props {
    children: ReactNode[]
}
function Carousel(props: Props) {
    const { children } = props
    const [selected, setSelected] = useState(0)

    useEffect(() => {
        const tick = setTimeout(() => {
            setSelected((selected + 1) % children.length)
        }, 2000)
        return () => {
            clearTimeout(tick)
        }
    })
    return <div className="carousel-container">
        {
            children.map((item, index) => {
                return <div
                    key={index}
                    className='carousel-item-wrapper'
                    style={{
                        left: `${100 * index}%`,
                        transform: `translateX(${-100 * selected}%)`
                    }}>{item}</div>
            })
        }
        <div className="indicator-box">
            {
                children.map((item, index) => {
                    return <div key={index} data-testid="indicator" className="indicator-item" onClick={() => setSelected(index % children.length)}>
                        {
                            selected === index && <div className="indicator-item-progress"></div>
                        }
                    </div>
                })
            }
        </div>
    </div>
}

export default Carousel
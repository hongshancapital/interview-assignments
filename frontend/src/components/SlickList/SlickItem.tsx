import React, { useEffect,  useState } from 'react'
import './index.css'

interface Props {
    interval: number
    currentIndex: number
    isActive?: boolean
    onClick?: (currentIndex: number) => void
}

const SlickItem = (props: Props) => {
    const {
        interval,
        currentIndex,
        isActive = false,
        onClick = () => {}
    } = props
    const [style, setStyle] = useState({})

    useEffect(() => {
        setStyle(isActive ? {
            width: '100%',
            transition: `width linear ${interval / 1000}s`
        } : {})
    }, [isActive, interval])

    const onClickFn = () => {
        onClick(currentIndex)
    }

    return (
        <span className='slick-item' onClick={onClickFn}>
            <span className="slick-item-pregress" style={style} />
        </span>
    )
}

export default SlickItem
import React from 'react'
import cn from 'classnames'
import style from './indicator.module.scss'

export interface Props {
    count: number
    index: number // start from 0
    duration: number // ms
}

function Indicator({ count, index, duration }: Props) {
    return (
        <div className={style.main}>
            {[...Array(count)].map((v, idx) => (
                <div
                    key={idx}
                    className={cn(style.line, { [style.active]: idx === index })}
                    style={{ animationDuration: `${duration}ms` }}
                />
            ))}
        </div>
    )
}

export default Indicator

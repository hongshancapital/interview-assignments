import React from 'react'
import { useDevideProgress } from '../../hooks/useDevideProgress'
import { useTimer } from '../../hooks/userTimer'
import { Bar } from './item'
import './group.css'

export interface IGroupProps {
    barCount: number;
    duration: number;
}

export const Group = (props: IGroupProps) => {
    const timers = useDevideProgress(props.duration, props.barCount)
    const { restartFrom } = useTimer()
    return (
        <div className="bar-group">
            {
                timers.map((timer$, i) => (
                    <Bar timer$={timer$} key={i} onClick={() => restartFrom(i * props.duration)}></Bar>
                ))
            }
        </div>
    )
}
import React from 'react'
import { Observable } from 'rxjs'
import { useObservable } from '../../hooks/useObservable'
import './item.css'

export interface IBarProps {
    timer$: Observable<number>;
    onClick: React.MouseEventHandler<HTMLDivElement>;
}
export const Bar = (props: IBarProps) => {
    const progress = useObservable(props.timer$, 0)
    const style: Record<string, string> = {
        '--progress': `${progress}%`,
    }
    return (
        <div className="bar" style={style} onClick={props.onClick}></div>
    )
}

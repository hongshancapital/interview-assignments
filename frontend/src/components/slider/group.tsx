import React, { useMemo } from 'react'
import { Item } from './item'
import './group.css'
import { useTimer } from '../../hooks/userTimer'
import { useObservable } from '../../hooks/useObservable'
import { map } from 'rxjs'
import { useSwipe } from '../../hooks/useSwipe'

export interface IGroupProps {
    config: {
       title: string;
       subtitle: string;
       backgroundColor: string;
       backgroundImage: string;
    }[];
    duration: number;
}

export const Group = (props: IGroupProps) => {
    const {timer$, pause, play, advance} = useTimer()
    const step$ = useMemo(
        () => timer$.pipe(
            map(elapsed => elapsed % (props.duration * props.config.length) / props.duration | 0),
        ),
        [props.duration, props.config.length, timer$]
    )
    const index = useObservable(step$, 0)

    useSwipe({
        onStart: pause,
        onStop: play,
        onMove: (percent) => {
            advance(percent * props.duration)
        }
    })
    return (
        <div
            className="slider-group-outer"
        >
            <div
                className="slider-group-inner"
                style={{
                    transform: `translateX(-${index}00%)`
                }}
            >
                {
                    props.config.map(({ title, subtitle, backgroundColor, backgroundImage}) => (
                        <Item
                            key={title}
                            title={title}
                            subtitle={subtitle}
                            backgroundColor={backgroundColor}
                            backgroundImage={backgroundImage}
                        />
                    ))
                }
            </div>
        </div>
    )
}

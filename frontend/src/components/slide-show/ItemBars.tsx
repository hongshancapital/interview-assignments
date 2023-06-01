import { useState, useMemo } from "react";
import './itembars.css'

interface ItemBarProps {
    time: number;
    active: boolean;
}

const ItemBar = ({time, active}: ItemBarProps) => {
    if(!active) {
        return (
            <div className="item-bar"/>
        );
    }
    return (
        <div className="item-bar active">
            <div className="item-bar progress" style={{
                animationName: 'progress-move',
                animationDuration: `${time / 1000}s`
            }}/>
        </div>
    );
};

interface ItemBarsProps {
    itemCount: number;
    time: number;
    currentIndex: number;
}

export const ItemBars = ({itemCount, time, currentIndex}: ItemBarsProps) => {
    const items = useMemo(() => {
        const r = [];
        for(let i=0;i<itemCount;i++){
            r.push(i);
        }
        return r;
    }, [itemCount]);
    return (<div className="item-bars">
        {items.map(i => (<ItemBar time={time} active={i === currentIndex}/>))}
    </div>);
};
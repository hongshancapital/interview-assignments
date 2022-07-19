import React, {FC, useEffect, useState} from 'react';
import s from './index.module.css';

interface TNavProps {
    count: number;
    duration?: number;
    defaultActiveIndex?: number;
    onActiveChange?: (nextIndex: number) => void;
}

const Nav: FC<TNavProps> = (props) => {

    const {count, duration = 3000, defaultActiveIndex = 0, onActiveChange} = props;

    const [activeIndex, setActiveIndex] = useState(defaultActiveIndex);
    const [animationPaused, setAnimationPaused] = useState(false)

    useEffect(() => {
        onActiveChange?.(activeIndex);
    }, [activeIndex])

    const handleAnimationend = () => {
        let next = activeIndex + 1;
        if (next === count) next = 0;
        setActiveIndex(next);
    }

    const handleMouseEnter = (itemIndex: number) => {
        if (itemIndex !== activeIndex) return;
        setAnimationPaused(true)
    }

    const handleMouseLeave = (itemIndex: number) => {
        if (itemIndex !== activeIndex) return;
        setAnimationPaused(false);
    }

    const handleClick = (itemIndex: number) => {
        setActiveIndex(itemIndex)
    }

    const navItems = [];
    for (let i = 0; i < count; i++) {
        const isActive = i === activeIndex;
        navItems.push(
            <div
                key={i}
                className={`${s.navItem} ${isActive ? s.active : ''}`}
                onMouseEnter={() => handleMouseEnter(i)}
                onMouseLeave={() => handleMouseLeave(i)}
                onClick={() => handleClick(i)}
            >
                {isActive && (
                    <div
                        className={s.animationDiv}
                        style={{
                            animationDuration: `${duration}ms`,
                            animationPlayState: animationPaused ? 'paused' : 'running'
                        }}
                        onAnimationEnd={handleAnimationend}
                    />
                )}
            </div>
        )
    }

    return <div className={s.navRoot}>
        {navItems}
    </div>
}

export default Nav;
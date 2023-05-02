import React, { FC, useMemo } from "react";
import { MouseEvent } from '../../index.d';
import './index.css';

interface DotsProps {
    slideCurrent: number,
    count: number,
    onChange?: (index: number) => void
}

const Dots: FC<DotsProps> = ({
    slideCurrent,
    count,
    onChange
}) => {

    //将数字转成数组方便map和记录索引
    const arrForCount: number[] = useMemo(() => Array.from({ length: count }, (__, index) => index) ?? [], [count]);

    const handeleClick = (index: number, e: MouseEvent) => {
        onChange?.(index);
        // e.stopPropagation();
    }

    return (
        <ul className="dot-container">
            {arrForCount.map(item => (
                <li
                    key={`dot_item_${item}`}
                    className={`${slideCurrent===item? 'actived-dot': ''} dot-item`}
                    onClick={(e) => handeleClick(item, e)}
                />
            ))}
        </ul>
    )
}

export default Dots;
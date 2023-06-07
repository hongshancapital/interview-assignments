import React, { FC, useMemo } from "react";
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

    const handeleClick = (index: number) => {
        onChange?.(index);
    }

    return (
        <ul className="dot-container">
            {arrForCount.map(item => (
                <li
                    key={`dot_item_${item}`}
                    className={`${slideCurrent===item? 'actived-dot': ''} dot-item`}
                    onClick={() => handeleClick(item)}
                />
            ))}
        </ul>
    )
}

export default Dots;
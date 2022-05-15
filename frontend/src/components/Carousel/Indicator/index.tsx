import React, { CSSProperties, useMemo } from 'react';
import './index.scss';

export interface IndicatorProps {
    /**当前索引 */
    curIndex: number;
    /**总数 */
    total: number;
    /**切换间隔(ms) */
    interval: number;
    /**点击切换页面回调 */
    onChange?: (index: number) => void;
}

export default function Indicator(props: IndicatorProps) {
    const items = useMemo(
        () =>
            Array(props.total).fill(0).map((_, i) => {
                if (i === props.curIndex) {
                    return { width: '100%', transition: `width ${props.interval}ms linear` } as CSSProperties;
                }
                return {};
            }),
        [ props.total, props.curIndex, props.interval ]
    );

    return (
        <div className='indicator-container' onClick={() => {}}>
            {items.map((it, i) => (
                <div className='indicator-item' key={i} data-index={i}>
                    <span className='indicator-fill' style={it} />
                </div>
            ))}
        </div>
    );
}

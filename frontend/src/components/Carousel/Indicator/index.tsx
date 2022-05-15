import React, { CSSProperties, useCallback, useMemo } from 'react';
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
    const { curIndex, total, interval, onChange = _ => _ } = props;
    const items = useMemo(
        () =>
            Array(total).fill(0).map((_, i) => {
                if (i === Number(curIndex)) {
                    return { width: '100%', transition: `width ${interval}ms linear` } as CSSProperties;
                }
                return {};
            }),
        [total, curIndex, interval]
    );
    const handleSwitch = useCallback((e) => {
        const { index } = e?.target?.dataset || {};
        if (index === undefined) return;
        onChange(index);
    }, [onChange]);

    return (
        <div className='indicator-container' onClick={handleSwitch}>
            {items.map((it, i) => (
                <div className='indicator-item' key={i} data-index={i}>
                    <span className='indicator-fill' style={it} />
                </div>
            ))}
        </div>
    );
}

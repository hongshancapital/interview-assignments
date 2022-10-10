import React, { useState, useEffect } from "react";

import "./index.css";

/**
 * 默认配置
 */
const ProgressConfig = {
    ACTIVE: false,
    DURATION: 2000
}

interface IProgress {
    /**
     * 是否激活状态
     * 激活状态才会有进度条动画
     */
    active?: boolean;
    /**
     * 进度条时长 默认为 2000ms
     */
    duration?: number;
}

const Progress = (props: IProgress) => {
    const [active, setActive] = useState(ProgressConfig.ACTIVE);

    useEffect(() => {
        setActive(props.active ? true : false);
    }, [props.active]);

    return (
        <div className="progress">
            <div
                className={`progress-schedule ${active ? "progress-active" : ''}`}
                style={{ animationDuration: `${props.duration || ProgressConfig.DURATION}ms`}}
            />
        </div>
    );
};

export { Progress };

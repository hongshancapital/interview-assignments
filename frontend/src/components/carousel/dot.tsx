/*
 * @Author: longsi
 * @Date: 2022-06-10 19:26:58
 * @LastEditors: longsi
 * @LastEditTime: 2022-06-14 15:55:40
 * @FilePath: /interview-assignments/frontend/src/components/carousel/dot.tsx
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import cn from 'classnames';
import React from 'react';

import styles from './css/dot.module.scss';

interface IDotProps {
    onClick: () => void;
    active: boolean;
    interval?: number | null;
}

export const Dot: React.FC<IDotProps> = ({ active, onClick, interval }) => {
    return (
        <div
            className={cn(styles.dot, { [styles["dot-active"]]: active })}
            onClick={onClick}
        >
            <div
                className={styles["dot-inner"]}
                style={{
                    animationDuration: `${interval}ms`,
                }}
            />
        </div>
    );
};
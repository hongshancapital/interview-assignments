import React, { FC } from 'react';
import cls from 'classnames';
import './index.scss';

interface IndicatorItemProps {
    active: boolean;
    duration: number;
    onClick?: React.MouseEventHandler;
}

const IndicatorItem: FC<IndicatorItemProps> = ({ active, duration, onClick }) => {
    return <div className='indicator-item' onClick={onClick}>
        <span className={cls('indicator-cover', { active })} style={{ animationDuration: `${duration}ms` }} />
    </div>;
}

export default IndicatorItem;
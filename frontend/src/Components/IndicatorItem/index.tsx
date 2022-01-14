import React, { FC } from 'react';
import { Item } from './style';

interface IndicatorItemProps {
    active: boolean; 
    duration: number;
    onClick?: React.MouseEventHandler;
}

const IndicatorItem: FC<IndicatorItemProps> = ({ active, duration, onClick }) => {
    return <Item className='indicator' active={active} duration={duration} onClick={onClick} />;
}

export default IndicatorItem;
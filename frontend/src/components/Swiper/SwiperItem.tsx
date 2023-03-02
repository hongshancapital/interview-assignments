import React, { ReactNode } from 'react';

import Style from './Swiper.module.scss';

const SwipeItem: React.FC<{ children?: ReactNode | undefined }> = (props) => {
    return <div className={Style['swiper-item']}>{props.children}</div>
}

export default SwipeItem;
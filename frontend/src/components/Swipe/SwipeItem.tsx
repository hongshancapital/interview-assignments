import React, { ReactNode } from 'react';

import Style from  './Swipe.module.scss';

const SwipeItem: React.FC<{ children?: ReactNode | undefined }> = (props) => {
    return <div className={Style['swipe-item']}>{props.children}</div>
}

export default SwipeItem;
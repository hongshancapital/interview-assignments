import React from "react";

type IProps = {
    style?: React.CSSProperties,
    children: React.ReactChildren | React.ReactChild,
}

const Item: React.FC<IProps> = (props) => {
    const { style = {}, children } = props;

    return (
        <div style={{
            width: '100%',
            display: 'flex',
            justifyContent: 'center',
            ...style
        }}>
            {children}
        </div>
    )
}

export default Item;
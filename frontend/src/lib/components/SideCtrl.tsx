import React, { PropsWithChildren, useContext, useEffect, useState } from "react";
import { ControllerContext } from './ControllerLayer';

interface Props {
    side: string;
}

export default function SideCtrl({ side }: Props) {
    const ctrlContext = useContext(ControllerContext);
    const { onSideClick } = ctrlContext as { onSideClick: any; };
    return (
        side === 'left'
            ? <span className="left" onClick={() => { onSideClick('left'); }}> {'<'} </span>
            : <span className="right" onClick={() => { onSideClick('right'); }}> {'>'} </span>
    );
}
import React, { FC, Children, useMemo } from "react";
import './cardLayout.css';
import { LayoutProps, Theme } from "./types";
import { classNames } from "../../utils";

export const CardLayout:FC<LayoutProps> = ({theme, info, image}) => {
    const containerClass = useMemo(() => classNames('card-layout-cotainer', {
            ['card-layout-container__white']: theme === Theme.WhiteTheme,
            ['card-layout-container__black']: theme === Theme.BlackTheme,
            ['card-layout-container__gray']: theme === Theme.GrayTheme
        })
    , [theme, classNames, Theme])
    return <div className={containerClass}>
        <div className='card-layout-title' >{info.title}</div>
        <div className='card-layout-tips'>{info.tips}</div>
        {image}
    </div>
}
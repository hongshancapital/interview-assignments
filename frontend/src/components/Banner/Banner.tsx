import React from "react";
import * as CSS from 'csstype';
import Style from './Banner.module.scss'

export interface BannerProps {
    title: Array<string>;
    desc: Array<string>;
    style: CSS.Properties<string | number>;
}
const Banner: React.FC<BannerProps> = (props: BannerProps) => {
    return <div style={props.style} className={Style["banner"]}>
        <div className={Style["slogan"]}>
            {props.title.map(item => <h1 key={item}>{item}</h1>)}
            {props.desc.map(item => <p key={item}>{item}</p>)}
        </div>
    </div>
}
export default Banner
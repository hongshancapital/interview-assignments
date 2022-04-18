import React, { FC, useMemo } from "react";
import './index.css'

interface BannerProps {
    title: string | string[];
    subtitle?: string | string[];
    style?: React.CSSProperties;
}

const Banner: FC<BannerProps> = (props) => {
    const { title, subtitle, style } = props;

    const renderTitle = useMemo(() => {
        return Array.isArray(title) 
            ? title.map((item: string) => (<h2 className="banner-container-title" key={item}>{item}</h2>))
            : <h2 className="banner-container-title">{title}</h2>;
    }, [title]);

    const renderSubtitle = useMemo(() => {
        if (!subtitle) return null;

        return Array.isArray(subtitle) 
            ? subtitle.map((item: string) => (<p className="banner-container-subtitle" key={item}>{item}</p>))
            : <p className="banner-container-subtitle">{subtitle}</p>;
    }, [subtitle]);

    return (
        <div className="banner-container" style={style}>
            {renderTitle}
            {renderSubtitle}
        </div>
    )
}

export default Banner

import React from 'react';
import { IgoodsDatas } from '../../hooks/useGoodsDatas';

import './index.scss';

interface IGoodsIntroProps {
    goodsInfo: IgoodsDatas
}

export default function GoodsIntro(props: IGoodsIntroProps) {
    const {
        title,
        details,
        img,
        backgroundColorTheme,
        fontColorTheme
    } = props.goodsInfo

    return (
        <div className="goods-container" style={{backgroundColor: backgroundColorTheme, color: fontColorTheme}}>
            {Array.isArray(title)
                ? title.map(titleItem => <div key={titleItem} className="goods-title">{titleItem}</div>)
                : <div className="goods-title">{title}</div>
            }
            {
				details && details.map(detailItem => <div key={detailItem} className="goods-detail">{detailItem}</div>)
			}
            
            <div className="goods-img">
                <img src={`/assets/${img}`}/>
            </div>
        </div>
    )
}
import React from 'react'
import "./index.css"


export interface CardProps{
    theKey: string         // key
    imgUrl: string     // 图片url
    titles: string[]    // 标题
    details: string[]   // 详情
    styles:{            // 样式合集    
        fontColor: string
        bgColor: string
    }
}

function Card({
    imgUrl, titles, details, styles
}:CardProps){

    return (<div className="card" style={{backgroundImage: `url(${imgUrl})`, color: styles.fontColor, backgroundColor: styles.bgColor}}>
        <div className='texts_wrapper'>
            {titles.map((title, index) =>{
                return <div key={`t${index}`} className='title'>{title}</div>
            })}
            {details.map((detail, index)=>{
                return <div key={`d${index}`}  className='text'>{detail}</div>
            })}
        </div>
    </div>)
}

export default Card
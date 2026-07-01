import React , { useRef, useEffect, useState } from "react";
import carouselSource from '../../CarouselSource';

const productList = carouselSource.map((source) =>{
    return (
        <div
            key={source.id}
            className="productList"
            style= {{
                width: '100%',
                height: '100%',
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                position: 'absolute',
                backgroundColor: source.backgroundColor,
                color: source.color,
            }}
        >
            <div style={{position: 'absolute', top: '256px'}}>
                { source.title.map((text) =>{
                    return (
                        <p key={text} style={{fontSize: '50px'}}>{text}</p>
                    )
                })}
                { source.desc.map((text) =>{
                    return (
                        <p key={text} style={{fontSize: '30px'}}>{text}</p>
                    )
                })}
            </div>

            <div>
                <img src={source.img} alt="1" style={{ width: '100%', bottom: '200px'}}></img>
            </div>

        </div>
    )
})

export default productList
/**
 * @author:shike
 * @description:轮播广告
 */
import React, { useEffect, useState } from 'react';
import { Carousel } from 'antd';
import './carouselIndex.scss';
import {list} from './data';

interface listInterface{
    id:number,
    title:string,
    content:string,
    img:string
}


const CarouselIndex = () => {
    const [textColor, setTextColor] = useState<string>('#fff'); 

    useEffect(() => {
        console.log("list :",list)
    },[]);
    //监听页面index，改变color
    const colorChange = (index:number) =>{
        if(index === 0){
            setTextColor('#fff')
        }else{
            setTextColor('#000')
        }
    }

    return(
        <div className='container'>
            <Carousel 
                autoplay 
                afterChange={colorChange}>
                {list.map((item:listInterface,index:number) => 
                    <div key={index} className='carousel'>
                        <div className="title" style={{backgroundImage:`url(${item.img})`,color:textColor}}>
                            <div className='tit-name'>{item.title}</div>
                            <div className='tit-content'>{item.content}</div>
                        </div>
                       
                    </div>
                )}
            </Carousel>
        </div>
    );
};
export default CarouselIndex;
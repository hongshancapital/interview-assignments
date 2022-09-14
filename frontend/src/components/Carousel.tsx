import React, { useState, useEffect, useRef } from 'react';
import useInterval from './useInterval'
import Carouselitem from './Carouselitem'
import IndicatorsItem from './IndicatorsItem'
import './Carousel.scss'

interface CProps {
    items?:Array<any>
    showIndicators?:boolean
    autoPlay?:boolean
    delay?:number
    [index:string]:any
}

const Carousel: React.FC<CProps> = (props) => {

    const items:Array<any> = props.items || [];
    const showIndicators:boolean = typeof props.showIndicators==='boolean' ? props.showIndicators : true;
    const autoPlay:boolean = typeof props.autoPlay==='boolean' ? props.autoPlay : true;
    const delay:number = props.delay ? props.delay>1000 ? props.delay : 1000 : 3000;
    const itemsLength:number = items.length || 1;

    const [wrapStyle, setWrapStyle] = useState<React.CSSProperties>({});
    const [activeIndex, setActiveIndex] = useState<number | null>(null);
    const [activeDelay, setActiveDelay] = useState<number | null>(delay);

    useEffect(() =>{
        setWrapStyle({
            width: `${itemsLength * 100}%`,
            transform: `translateX(${ ( activeIndex || 0 ) * -1 * ( 100 / itemsLength ) }%)`,
        })
    },[itemsLength,activeIndex]);

    const active = useRef({});
    active.current = ()=>{
        setActiveIndex( activeIndex===null || ( activeIndex && activeIndex >= items.length-1 ) ? 0 : activeIndex + 1);
    }
    useInterval(active, autoPlay ? activeDelay : null);

    const indicatorsClick = (e:React.MouseEvent,index:number):void =>{
        e.stopPropagation();
        setActiveIndex(index);
        setActiveDelay(null)
    }
    const indicatorsMouseLeave =  (e:React.MouseEvent,index:number):void =>{
        e.stopPropagation();
        setActiveDelay(delay)
    }

    const contentHtml:Array<JSX.Element> = items.map((item:any,index:number)=>
        <Carouselitem  key={index} title={item.title} describe={item.describe} bgImage={item.bgImage}/>
    )
    const indicatorsHtml:Array<JSX.Element> = items.map((item:any,index:number)=>{
        const activeStyle: React.CSSProperties = 
        ( index === activeIndex ) || ( !autoPlay && activeIndex === null && index === 0 ) ? 
        {
            transition : `all ${delay/1000}s ease-in`,
            width: `18px`
        }:{
            width: `0px`
        }
        return <IndicatorsItem key={index} activeStyle={activeStyle}
                indicatorsClick={(e:React.MouseEvent)=>{indicatorsClick(e,index);}} 
                indicatorsMouseLeave={(e:React.MouseEvent)=>{indicatorsMouseLeave(e,index);}} />
    })

    return (
        <div className='Carousel' role={props.role}>
            <div className='wrap' style={ wrapStyle }>
                {contentHtml}
            </div>
            { showIndicators && <div className='indicators'>
                { indicatorsHtml }
            </div> }
        </div>
    );
}

export default Carousel;
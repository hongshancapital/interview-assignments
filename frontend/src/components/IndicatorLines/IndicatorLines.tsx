import React from "react";
import Line from './Line'
import './IndicatorLines.css'
interface IndicatorLinesProps{
    linesLength:number,
    animationTime:number,
    currentPage:number
}
function IndicatorLines(props:IndicatorLinesProps){
    let indicators = []
    for(let i=0;i<props.linesLength;i++){
        indicators.push(<Line currentPage={props.currentPage} pageIndex={i} animationTime={props.animationTime} key={i}/>)
    }
    return <div className="indicator-wrap">
        {indicators}
    </div>
}
export default IndicatorLines
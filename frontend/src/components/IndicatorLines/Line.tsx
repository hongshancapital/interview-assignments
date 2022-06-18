import React from "react";
import './Line.css'
interface LineProps {
    animationTime:number,
    currentPage:number,
    pageIndex:number
}
function Line(props:LineProps){
    return <div className="indicator-line">
        <div className="white-inside" style={{animation:props.currentPage===props.pageIndex?`moveRight ${props.animationTime}s linear`:''}}></div>
    </div>
}
export default Line
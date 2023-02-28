import './index.css'

import { IndicatorProps } from './types';
import { CSSProperties, FunctionComponent,useCallback } from "react";

const Index: FunctionComponent<IndicatorProps> = (props) => {
    const { size, speed, currentIndex } = props
    const renderItems = useCallback(()=>{
        if (!size) return []
        let style: CSSProperties = {};
        return new Array(size).fill(0).map((item,index)=>{
            if (currentIndex === index) {
                style.animation = `move ${speed}ms infinite`;
            } else {
                style.animation = 'none'
            }
            return (
                <li key={index} className='indicators_label'>
                      <span className='indicators_span' style={{...style}}></span>
                </li>
            )
        })
      },[currentIndex, size, speed])
    return (
        <ul className="indicators">
                 {renderItems()}
        </ul>
    )
}

export default Index;
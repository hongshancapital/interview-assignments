
import React from "react";
import { dotProps } from "./../type"
function IndexDotItem(props: dotProps) {
  return <li className="indexDot-item" onClick={() => { props.tabChange(props.index) }} onMouseEnter={() => { props.stopAnimation() }} onMouseLeave={() => { props.startAnimation() }}>
    <div className="indexDot-item-mask" style={{ width: `${props.currIndex === props.index ? props.countdown : 0}px` }}></div></li>
}
export default IndexDotItem;
import React from "react";
import "./index.css";
import type { IContainerProps } from './IContainerProps.type'; 

const Container: React.FC<IContainerProps> = (props) => {
  return (
    <div className={`container ${props.className || ''}`} style={props.style}>{props.children}</div>
  )
}

export default Container;

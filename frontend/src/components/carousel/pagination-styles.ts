import { CSSProperties } from "react";

export const getPaginationStyles: CSSProperties = {
  position: 'absolute',
  bottom: '36px',
  left: '50%',
  display: 'flex',
  justifyContent: 'center',
  transform: 'translateX(-50%)'
}

export const pageStyle: CSSProperties = {
  borderRadius: '5px',
  overflow: 'hidden',
  margin: '0 5px',
  width: '30px',
  height: '2px',
  bottom: '20px',
  background: ' white'
}

export const dotCommonStyle: CSSProperties = {
  width: '100%',
  height: '100%',
  background: 'rgb(118, 117, 117)'
}

export const dotActiveStyle: CSSProperties = {
  transition: 'transform 3s',
  transformOrigin: 'top right',
  transform: 'scaleX(0)',
}

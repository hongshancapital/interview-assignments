import React,{memo, ReactElement, CSSProperties} from "react";
import "./Banner.scss";

export type BannerProps = {
  id: string
  title:string | ReactElement
  desc?: string | ReactElement
  styles: CSSProperties
}

const Banner = (props:BannerProps) => {

  const {title, desc, styles } = props


  return (<div className='banner' style={{...styles}}>
    <div className='title'>{title}</div>
    {!!desc && <div className='desc'>{desc}</div>}
  </div>)
}

export default memo(Banner)
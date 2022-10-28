import React, { useMemo } from 'react';
import './index.scss'

type SlideProps = {
    activeIndex: number,
    slideList: Carousel.slideItem[]
};

const Slide = ({activeIndex,slideList}:SlideProps) => {
    const ulWidth = useMemo(()=>{
        return slideList.length*100+'vw'
    },[slideList])
    const left = useMemo(()=>{
        return activeIndex<slideList.length?(activeIndex)*-100+'vw':'0'
    },[activeIndex])

  return (
    <div className="content">
      <ul style={{width: ulWidth,left: left}}>
        {
            slideList.map((v,i) => <li key={i} style={{color:v.color,backgroundColor:v.backgroundColor}}>
                {v.subject.map((s,si)=><p key={si} style={{fontSize:s.fontSize,fontWeight:s.fontWeight}}>{s.text}</p>)}
                <img src={require(`../../../assets/image/${v.goodsImgURL}`)} alt=''/>
            </li>)
        }
      </ul>
    </div>
  );
}

export default Slide;

import React, { useState } from 'react';

//区域
const container: React.CSSProperties = {
  width: '100%',
  display: 'flex',
  flexDirection: 'column',
  overflow: 'hidden',
};
const containerRef: React.CSSProperties = {
  margin: 0,
  height: '100vh',
  display: 'flex',
  flexDirection: 'row',
  alignItems: 'center',
  transition: "all linear 0.2s",
};

// 内容
const itemStyle: React.CSSProperties = {
  margin: 0,
  width: '100vw',
  height: '100vh',
  background: require('../../assets/iphone.png'),
  backgroundSize: 'auto 1080px,cover',
  backgroundPositionX:'center',
  backgroundPositionY:'bottom',
  backgroundRepeat: 'no-repeat',
  textAlign: 'center',
};
const textStyle: React.CSSProperties = {
  padding:'10% 20%',
  wordBreak:'break-all',
  whiteSpace:'normal'
}
const titleStyle: React.CSSProperties = {
  fontSize:'70px',
  fontWeight:'bold',
}
const descStyle: React.CSSProperties = {
  fontSize:'35px',
}

// 切换按钮
const actionStyle: React.CSSProperties = {
  width: '100%',
  position: 'relative',
  top: '-50px',
  display: 'flex',
  flexDirection: 'row',
  justifyContent: 'center',
  alignItems: 'center',
};
const actionItemStyle: React.CSSProperties = {
  height:'10px',
  lineHeight:'10px',
  cursor: 'pointer',
};
const actionLineStyle: React.CSSProperties = {
  width: '50px',
  height: '3px',
  margin: '3px 5px',
  backgroundColor: '#fff',
};

const MyCarousel=({arr=[{title:'',desc:'',prices:'',style:{},url:''}]}) => {

    const [curIndex, setcurIndex] = useState(0);

    let pre = Date.now()
    const onChange = (index: number) => {
      const now = Date.now()

      if(now-pre>3000){
        pre = Date.now()
        setcurIndex(index)
        clearInterval(timer)
      }
    };

    //自动切换
    const timer = setInterval(()=>{
      let temp = curIndex + 1

      if(temp<arr.length){
        onChange(temp)
      }else{
        onChange(0)
      }
    },3000)


    // 切换按钮
    const actionItem = arr.map((i, index) => {
      return (
        <div style={actionItemStyle} onClick={() => onChange(index)} key={index}>
        <div style={{ ...actionLineStyle, backgroundColor: curIndex === index ? '#f5f5f5' : '#8d8787' }} ></div>
        </div>
      )
    })

    // 内容
    const carouselItem = arr.map((i, index) =>
      <div key={index}>
        <div style={{ ...itemStyle, backgroundImage: i.url,...i.style }}>
          <div style={textStyle}>
            <div style={titleStyle}>{i.title}</div>
            <div style={descStyle}>{i.desc}</div>
            <div style={descStyle}>{i.prices}</div>
          </div>
        </div>
      </div>
    )

    return (
      <div>
        <div style={container}>
          <div style={{ ...containerRef, transform: 'translate(-' + 100 * curIndex + 'vw,0)' }}>
            {carouselItem}
          </div>
          <div style={actionStyle}>
            {actionItem}
          </div>
        </div>
      </div>
    );
  }
export default React.memo(MyCarousel);
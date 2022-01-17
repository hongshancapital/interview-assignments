import React,{FunctionComponent,memo,useState,useRef,useCallback,useEffect} from 'react';
import {Container} from '../../styles'
import Scroller from '../../libs/scroller'
import styled from 'styled-components'
import shortid from 'shortid';

const Box = styled(Container)`
  display:flex;
  margin-left:auto;
  margin-right:auto;
  height:100%;
  align-items: center;
  flex-direction: row;
  overflow-x: hidden;
  scroll-behavior: smooth;
`

const Content = styled(Container)`
  height:100%;
  width:100vw;
  flex-shrink: 0;
`




type SliderProps = {
  duration:number;
  items:JSX.Element[];
  countingCallback:(timePercent:number,id?:number)=>void;
}

const App:FunctionComponent<SliderProps> = (props) => {
  const ref = useRef<HTMLDivElement>(null)
  const [sections,setSections] = useState(()=>{
    if(props.items.length >= 2){
      return [props.items[0],props.items[1]]
    }else{
      return [props.items[0],props.items[0]]
    }
  })
  //简单懒加载，播放当前时，提前将下一个待播放内容插入
  const lazyLoad = useCallback((scroller:Scroller)=>{
    const next = scroller.currentPlay+1
    if(props.items[next]){
      setSections((content)=>{
        if(content.length === props.items.length){//全部加载完成
          return content
        }else{
          return [...content,props.items[next]]
        }
      })
    }
  },[])

  //创建滚动器
  useEffect(()=>{
    if(ref && ref.current){
      new Scroller({
        wrapper:ref.current,
        interval:props.duration,
        onChange:lazyLoad,
        countingCallback:props.countingCallback,
      })

    }
  },[])

  return(
    <Box ref={ref}>
      {sections.length > 0 &&
        sections.map(item => {
          return <Content key={shortid.generate()}>{item}</Content>
        })
      }
    </Box>
  )
}


export default memo(App)

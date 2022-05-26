import React, { useState, useEffect, useMemo, useCallback, Attributes, } from 'react';
import { useProcessPlay } from '../../hooks';
import { ContainerStyled, ListStyled, ItemStyled, CtrlListStyled, ProcessStyled } from './styled';


interface Props extends Attributes {
  className?: string;
  duration?: number;
  stepDuration?: number;
  children: JSX.Element[];
}

interface ProcessProps extends React.HTMLAttributes<HTMLSpanElement> {
  value: number;
}

function Process(props: ProcessProps) { // 进度条小组件
  const { value = 0, ...others } = props;
  const widthStyle = useMemo(() => {
    return {
      width: `${value}%`,
    }
  }, [value]);
  return <ProcessStyled {...others}>
    <span style={widthStyle}></span>
  </ProcessStyled>
}

function Carousel(props: Props) {
  const { duration = 1200, stepDuration = 5000, children, ...others } = props;
  const [index, setIndex] =  useState(1);
  const { value, start, stop, reset} = useProcessPlay({
    duration: stepDuration,
    loop: true,
    onEnd() {
      setIndex(val => {
        if (val >= nodes.length) {
          return 1
        }
        return val + 1
      });
    }
  });
 
  const nodes = useMemo(() => { // 播放列表节点数组
    return  React.Children.map(children, child => <ItemStyled>{child}</ItemStyled>)
  }, [children])

  const onMouseOver = useCallback(() => { // 鼠标移入暂停
    stop()
  }, [stop]);

  const onMouseOut = useCallback(() => { // 鼠标移出播放
    start();
  }, [start]);

  useEffect(() => {
    start();
  }, [start])

  return (
    <ContainerStyled {...others} onMouseOver={onMouseOver} onMouseOut={onMouseOut}>
      <ListStyled index={index} duration={duration} >
        {nodes}
      </ListStyled>
      <CtrlListStyled>
        {
          nodes.map((item, idx) => (
            <Process key={idx} value={idx + 1 === index ? value : 0} onMouseOver={() => {
              // 鼠标移入切换 index 重置停留进度
              if (idx + 1 !== index) {
                setIndex(idx + 1);
                reset()
              }
            }}/>
          ))
        }
      </CtrlListStyled>
    </ContainerStyled>
  );
}

export default Carousel;

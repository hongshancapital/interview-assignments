import React, { useState, useEffect } from 'react';
import styled from 'styled-components'

interface ISub {
  children: any,
  subMsg: string
}
const StyledDivSub = styled.div`
    width: 100%;
    height: 100%;
    flex: 0 0 auto;
    position:relative;
`
const StyledDivChild = styled.div`
    width: 100%;
    height: 100%;
`
export default function SubPage(props: ISub) {

  return <StyledDivSub>
    <StyledDivChild>
      {props.children}
    </StyledDivChild>
  </StyledDivSub>


}
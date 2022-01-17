import styled from 'styled-components';


/********************Color Style*********************/
export const primary = '#FFFFFF'
export const black = '#111'


/********************Theme Style*********************/
export const theme = {
  color:{
    primary,
    black,
  },
  background: primary,
  fontColor:black,
  borderWidth:"1px",
  fontWeight:600,
  textWeight:400,
  textSize:'1.4vw',
  titleSize:'4vw',
  padding:'16px',
  margin:'8px',
  minorSpacing:'8px',
  normarlSpacing:'16px',
  largeSpacing:'24px',
  horizontalSpacing:'24px 0',
  veticalSpacing:'0 16px'
}




/********************Container Style*********************/
export const StyledBlock = styled.div`
  padding: 0 ${props => props.theme.margin};
  display: block;
`;

export const StyledSpan = styled.span`
  margin: 0 ${props => props.theme.margin} 0 0;
  display: inline;
`;

export const Container = styled.div`

`;

interface WrapperProps {
  theme?: string;
}

export const Wrapper = styled.div.attrs((props: WrapperProps) => ({
  wrapperTheme: props.theme,
}))<WrapperProps>`
  display:flex;
  flex-direction:column;
  height:100%;
  align-items: center;
  background: ${props => props.wrapperTheme === 'dark' ? '#111':props.wrapperTheme};
  color: ${props => props.wrapperTheme === 'dark' ? '#fff':'#111'};
`;

/********************Font Style*********************/
export const TitleFont = styled.p`
  font-size:  ${props => props.theme.titleSize};
  color: ${props => props.color};
  margin: 20px;
  margin-top:auto;
  font-weight: ${props => props.theme.fontWeight};
  white-space: pre-wrap;
`;


export const TextFont = styled.p`
  margin: 0;
  display: inline-block;
  font-size: ${props => props.theme.textSize};
  font-weight: ${props => props.theme.textWeight};
  color: ${props => props.color};
  white-space: pre-wrap;
`;

export const NoWrapContentFont = styled.p`
  margin: 0 ;
  vertical-align: middle;
  font-size: ${props => props.theme.fontSize};
  color: ${props => props.color || props.theme.fontColor};
`;
/********************Img Style*********************/
export const Img = styled.img`
  max-height:calc(40%);
  max-width:100%;
  margin-bottom:auto;
`

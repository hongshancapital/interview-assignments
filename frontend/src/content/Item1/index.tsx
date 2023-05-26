import styled from "styled-components";
import image from '../../assets/iphone.png';
import {backgroundStyle, containerStyle, descriptionStyle, titleStyle} from "../layout";

const Container = styled.div`
  ${containerStyle};
  background: #000;
`

const Background = styled.img`
  ${backgroundStyle};
`;

const Title = styled.div`
  ${titleStyle};
  color: #fff;
`;

const Description = styled.div<{ $isFirst?: boolean }>`
  ${descriptionStyle};
  color: #fff;
  margin-top: ${props => props.$isFirst ? '10px' : ''};
`;

export default function Item1() {
  return (
    <Container>s
      <Background src={image} />
      <Title>xPhone</Title>
      <Description $isFirst>Lots to love. Less to spend.</Description>
      <Description>Starting at $399.</Description>
    </Container>
  )
}
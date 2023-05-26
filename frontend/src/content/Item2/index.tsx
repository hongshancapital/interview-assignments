import styled from "styled-components";
import image from '../../assets/tablet.png';
import {backgroundStyle, containerStyle, descriptionStyle, titleStyle} from "../layout";

const Container = styled.div`
  ${containerStyle};
`;
const Background = styled.img`
  ${backgroundStyle};
`;

const Title = styled.div`
  ${titleStyle};
  color: #000;
`;

const Description = styled.div<{ $isFirst?: boolean }>`
  ${descriptionStyle};
  color: #000;
  margin-top: ${props => props.$isFirst ? '10px' : ''};
`;

export default function Item2() {
  return (
    <Container>
      <Background src={image} />
      <Title>Tablet</Title>
      <Description $isFirst>Just the right amount of everything.</Description>
    </Container>
  )
}
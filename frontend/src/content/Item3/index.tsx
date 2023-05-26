import styled from "styled-components";
import image from '../../assets/airpods.png';
import {backgroundStyle, containerStyle, descriptionStyle, titleStyle} from "../layout";

const Container = styled.div`
  ${containerStyle};
`
const Background = styled.img`
  ${backgroundStyle};
`;

const Title = styled.div`
  ${titleStyle};
  color: #000;
`;

export default function Item3() {
  return (
    <Container>
      <Background src={image} />
      <Title>Buy a Tablet or xPhone for college.</Title>
      <Title>Get arPods.</Title>
    </Container>
  )
}
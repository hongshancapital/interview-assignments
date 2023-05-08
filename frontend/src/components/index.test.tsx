import React from 'react';
import { render, fireEvent, screen } from '@testing-library/react';
import Carousel from '../components/Carousel/index'
import imgIPhone from '../assets/iphone.png'
import imgTablet from '../assets/tablet.png'
import imgAirPods from '../assets/airpods.png'

beforeEach(()=>{
    jest.clearAllMocks();
})

describe('renders carousel component correctly', () => {
  const Component = () => (
    <Carousel items={[{
      id: '1',
      color: '#fff',
      backgroundColor: '#111111',
      title: ['xPhone'],
      contents: ['Lots to love. Less to spend.', 'Starting at $399.'],
      bg: imgIPhone
    },
    {
      id: '2',
      color: '#000',
      backgroundColor: '#FAFAFA',
      title: ['Tablet'],
      contents: ['Just the right amount of everything.'],
      bg: imgTablet
    },
    {
      id: '3',
      color: '#000',
      backgroundColor: '#F1F1F1',
      title: ['Buy a Tablet or xPhone for college.', 'Get airPods.'],
      bg: imgAirPods
    }]} delay={3000}/>
  )
  test('click dots',()=>{
    const { container } = render(<Component />)
    const dots = container.children[0].querySelectorAll(".indicator__track");
    //click dots[0] show first page, the title should be xPhone
    fireEvent.click(dots[0]);
    expect(screen.getByText("xPhone")).not.toBeNull()
    //click dots[1] show second page, the title should be Tablet
    fireEvent.click(dots[1]);
    expect(screen.getByText("Tablet")).not.toBeNull()
    //click dots[2] show second page, the title should be Tablet
    fireEvent.click(dots[2]);
    expect(screen.getByText("Buy a Tablet or xPhone for college. Get airPods.")).not.toBeNull()
})});

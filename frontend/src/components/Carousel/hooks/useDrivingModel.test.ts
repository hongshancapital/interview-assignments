import { renderHook, act } from '@testing-library/react-hooks'
import { CarouselItem } from '../types';
import useDrivingModel from './useDrivingModel'

test('hooks useDrivingModel test', () => {
  const initialParams: CarouselItem[] = [
    {
      title: 'Tablet',
      theme: 'Lighten',
      descriptions: ['Just the right amount of everything.'],
      image: 'https://img.alicdn.com/imgextra/i1/O1CN01vgHRN61bd5usKPMkZ_!!6000000003487-0-tps-290-290.jpg'
    },
    {
      title: 'xPhone',
      theme: 'Darken',
      descriptions: ['Lots to love,Less to spend.', 'Starting at $399'],
      image: 'https://img.alicdn.com/imgextra/i3/O1CN01kWJzWD1gcFGJeTHik_!!6000000004162-0-tps-264-316.jpg'
    },
    {
      title: 'Buy a Tablet or xPhone for college. Get arPods.',
      theme: 'Lighten',
      descriptions: [],
      image: 'https://img.alicdn.com/imgextra/i3/O1CN01VAEHLG1joYkvCuNVj_!!6000000004595-0-tps-200-200.jpg'
    }
  ];
  const { result } = renderHook(() => useDrivingModel(initialParams))
  
  expect(result.current).not.toBeUndefined();
  if (result.current) {
    const tablet = result.current[0];
    expect(tablet.data.title).toBe('Tablet');
    const xPhone = result.current[tablet.next];
    expect(xPhone.data.title).toBe('xPhone');
    const buyxxx = result.current[tablet.prev];
    expect(buyxxx.data.title.indexOf('Buy')).toBe(0);
  } else {
    expect(1).toBe(2);
  }
})
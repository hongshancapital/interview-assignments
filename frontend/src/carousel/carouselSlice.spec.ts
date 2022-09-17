import carouselReducer, {
  CarouselState,
  scrollTo,
} from './carouselSlice';

describe('carousel reducer', () => {
  const initialState: CarouselState = {
    active: 2,
    imgInfo: [
      {
        position: 'prev',
        name: 'iphone',
        imgName: 'iphone.png',
        titles: ['xPhone'],
        subtitles: ['Lots to love. Less to spend.', 'Starting at $399']
      },
      {
        position: '',
        name: 'tablet',
        imgName: 'tablet.png',
        titles: ['Tablet'],
        subtitles: ['Just the right amount of everything.'] 
      },
      {
        position: 'active',
        name: 'airpods',
        imgName: 'airpods.png',
        titles: ['Buy a Tablet or xPhone for college.', 'Get airpods'],
        subtitles: [] 
      }
    ] 
  };

  it('should handle initial state', () => {
    expect(carouselReducer(undefined, { type: 'unknown' })).toEqual({
      active: 0,
      imgInfo: [
        {
          position: 'active',
          name: 'iphone',
          imgName: 'iphone.png',
          titles: ['xPhone'],
          subtitles: ['Lots to love. Less to spend.', 'Starting at $399']
        },
        {
          position: '',
          name: 'tablet',
          imgName: 'tablet.png',
          titles: ['Tablet'],
          subtitles: ['Just the right amount of everything.'] 
        },
        {
          position: 'prev',
          name: 'airpods',
          imgName: 'airpods.png',
          titles: ['Buy a Tablet or xPhone for college.', 'Get airpods'],
          subtitles: [] 
        }
      ] 
    });
  });

  it('should handle scroll', () => {
    const actual = carouselReducer(initialState, scrollTo({ active: 0 }));
    expect(actual.active).toEqual(0);
    expect(actual.imgInfo[0].position).toEqual('active');
    expect(actual.imgInfo[2].position).toEqual('prev');
  });

});

export {}

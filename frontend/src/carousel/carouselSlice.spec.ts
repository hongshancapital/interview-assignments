import carouselReducer, {
  CarouselState,
  scrollTo,
} from './carouselSlice';

describe('carousel reducer', () => {
  const initialState: CarouselState = {
    active: 2,
    imgInfo: [
      {
        condition: 'prev',
        name: 'iphone',
        imgName: 'iphone.png',
        titles: ['xPhone'],
        subtitles: ['Lots to love. Less to spend.', 'Starting at $399']
      },
      {
        condition: '',
        name: 'tablet',
        imgName: 'tablet.png',
        titles: ['Tablet'],
        subtitles: ['Just the right amount of everything.'] 
      },
      {
        condition: 'active',
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
          condition: 'active',
          name: 'iphone',
          imgName: 'iphone.png',
          titles: ['xPhone'],
          subtitles: ['Lots to love. Less to spend.', 'Starting at $399']
        },
        {
          condition: '',
          name: 'tablet',
          imgName: 'tablet.png',
          titles: ['Tablet'],
          subtitles: ['Just the right amount of everything.'] 
        },
        {
          condition: 'prev',
          name: 'airpods',
          imgName: 'airpods.png',
          titles: ['Buy a Tablet or xPhone for college.', 'Get airpods'],
          subtitles: [] 
        }
      ] 
    });
  });

  it('should start scroll if `condition` === `active` ', () => {
    const actual = carouselReducer(initialState, scrollTo({ active: 0 }));
    expect(actual.active).toEqual(0);
    expect(actual.imgInfo[0].condition).toEqual('active');
    expect(actual.imgInfo[2].condition).toEqual('prev');
  });

  it('should not start scroll if `condition` !== `active` ', () => {
    const actual = carouselReducer(initialState, scrollTo({ active: 0 }));
    expect(actual.imgInfo[1].condition).toEqual('');
  });

});

export {}

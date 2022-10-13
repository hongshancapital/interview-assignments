import { createSlice } from '@reduxjs/toolkit';

export interface ImgInfo {
  condition: string;
  name: string;
  imgName: string;
  titles: string[];
  subtitles: string[]; 
}

export interface CarouselState {
  active: number;
  imgInfo: ImgInfo[];
}

const initialState: CarouselState = {
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
};

const buildInfo = (imgInfo: ImgInfo[], active: number) => {
  const prevIndex = (active - 1 + imgInfo.length) % imgInfo.length;
  return imgInfo.map((info, index) => {
    
    if(index === active) {

      return {
        ...info,
        condition: 'active'
      }
    }

    if(index === prevIndex) {

      return {
        ...info,
        condition: 'prev'
      }
    }

    return {
      ...info,
      condition: ''
    }
  });
}

export const carouselSlice = createSlice({
  name: 'carousel',
  initialState,
  reducers: {

    scrollTo: (state, action) => {
      const { active } = action.payload;

      Object.assign(state, {
        active,
        imgInfo: buildInfo(state.imgInfo, active) 
      });

    },
  },
});

export const { scrollTo } = carouselSlice.actions;

export default carouselSlice.reducer;

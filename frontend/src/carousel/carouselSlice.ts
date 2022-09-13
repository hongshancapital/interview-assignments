import { createAsyncThunk, createSlice, PayloadAction } from '@reduxjs/toolkit';
import { RootState, AppThunk } from '../app/store';

export interface CarouselState {
  value: number;
  status: 'idle' | 'loading' | 'failed';
}

const initialState: CarouselState = {
  value: 0,
  status: 'idle',
};

export const carouselSlice = createSlice({
  name: 'carousel',
  initialState,
  reducers: {
    increment: (state) => {
        state.value += 1;
    },
  },
});

export const { increment } = carouselSlice.actions;

export default carouselSlice.reducer;

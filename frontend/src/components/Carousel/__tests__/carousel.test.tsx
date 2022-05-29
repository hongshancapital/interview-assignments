import { renderHook } from '@testing-library/react-hooks';
import { useCarousel } from '../useCarousel';

describe('carousel hooks', () => {
	it('currentIndex auto change', () => {
		const { result } = renderHook(() => useCarousel(2, 1000));
		expect(result.current.currentIndex).toBe(0);
		expect(result.current.translateX).toBe('0%');

		setTimeout(() => {
			expect(result.current.currentIndex).toBe(1);
			expect(result.current.translateX).toBe('-100%');
		}, 1000)

		setTimeout(() => {
			expect(result.current.currentIndex).toBe(0);
			expect(result.current.translateX).toBe('0%');
		}, 2000)
	})
})
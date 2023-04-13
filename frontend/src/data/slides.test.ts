import SLIDES from './slides';

describe('slides data', () => {
    it('is not empty', () => {
        expect(SLIDES.length).toBeGreaterThan(0);
    });
});

/**
 * @class SwiperAdapter
 * @description swiper view adapter
 * @author benye.deng
 */
export class SwiperAdapter {
    /** payload for swiper */
    static getPayload () {
        return {
            autoPlay: true,
            sliders: [
                require('../assets/airpods.png'),
                require('../assets/iphone.png'),
                require('../assets/tablet.png')
            ],
            
        }
    }
}
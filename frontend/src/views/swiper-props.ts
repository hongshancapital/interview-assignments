import PropTypes, { InferProps } from 'prop-types';

const SwiperViewPropTypes = {
    autoPlay: PropTypes.bool.isRequired,
    sliders: PropTypes.array.isRequired
}

export type SwiperViewProps = InferProps<typeof SwiperViewPropTypes>;

export const SwiperViewDefaultProps = {
    autoPlay: true,
    sliders: []
}
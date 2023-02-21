import imageTablet from '../../../assets/tablet.png';

interface Props {


}

const PageOne: React.FC<Props> = (props) => {
    return (
        <div className={'page-wrapper  tablet fx-column fx-v-center '}
             style={{ backgroundImage: `url(${imageTablet})` }}>
            <div className={'title mt200'}>Tablet</div>
            <div className={'desc'}>Just the right amount of everything</div>
        </div>
    );
};

export default PageOne;

import imageAirpods from '../../../assets/airpods.png';

interface Props {


}

const PageOne: React.FC<Props> = (props) => {
    return (
        <div className={'page-wrapper  airpods fx-column fx-v-center '}
             style={{ backgroundImage: `url(${imageAirpods})` }}>
            <div className={'title mt200'}>Buy a Tablet or xPhone for college.</div>
            <div className={'title'}>Get arPods</div>
        </div>
    );
};

export default PageOne;

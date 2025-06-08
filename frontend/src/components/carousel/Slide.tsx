import "./slide.css";

interface SlideProps {
    height?: string;
    width?: string;
    title: string;
    subTitle?: string;
    bgPic?: string;
    bgColor?: string;
    color?: string;
}

export const Slide = ({ height, width, title, subTitle, bgPic, bgColor, color }: SlideProps) => {
    return (<div className="slide" style={{
        height,
        width,
        backgroundImage: `url(${bgPic})`,
        backgroundColor: bgColor,
        color: color || 'black',
    }} role="slide">
        <h1><MultilineText text={title} /></h1>
        <h2><MultilineText text={subTitle} /></h2>
    </div>)
};

const MultilineText = ({text}:{text?:string}) => {
    if(!text){
        return null;
    }
    const lines = text.split('\n');
    return (<>
        {lines.map((line, i) => (<div key={i+line}>{line}</div>))}
    </>)
};
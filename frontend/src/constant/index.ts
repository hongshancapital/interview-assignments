import { SourceItem } from "../types"

const sourceArr:Array<SourceItem> = [
    {
        title:'xPhone',
        subTitle:'Lots to love.Less to spend. \n Starting at $399.',
        style:{
            width:'100%',
            height:'100%',
            color:'white',
            fontWeight:'bold',
            fontSize:'34px',
            backgroundImage: `url(${require('../assets/iphone.png')})`,
            backgroundSize:'50% auto',
            backgroundRepeat:'no-repeat',
            backgroundColor:'#111'
        }
    },
    {
        title:'Tablet',
        subTitle:'Just the right amount of everything',
        style:{
            width:'100%',
            height:'100%',
            color:"#000",
            fontWeight:'bold',
            fontSize:'34px',
            backgroundImage: `url(${require('../assets/tablet.png')})`,
            backgroundColor:'#FAFAFA'
        }
    },
    {
        title:'Buy a Tablet or xPhone of college. \n Get airPods',
        style:{
            width:'100%',
            height:'100%',
            color:"#000",
            fontWeight:'bold',
            fontSize:'34px',
            backgroundImage: `url(${require('../assets/airpods.png')})`,
            backgroundColor:'#F1F1F3'
        }
    }
]

export {sourceArr}
import React from 'react';
import Carousel from 'react-material-ui-carousel';
import {CardMedia} from "@mui/material";
import {makeStyles} from "@mui/styles";

const useStyle = makeStyles(() => ({
  container: {
    height: '100vh',
  },
  root: {
    height: '85vh',
    boxShadow: 'none'
  },
  img: {
    height: '100%'
  },
  font: {
    width: '80%',
    position: 'absolute',
    top: '20%',
    left: '50%',
    transform: 'translate(-50%)'
  },
  firstFont: {
    position: 'absolute',
    top: '20%',
    left: '50%',
    transform: 'translate(-50%)',
    color: "white"
  },
  title: {
    fontSize: 60
  },
  description: {
    fontSize: 30,
    whiteSpace: 'pre-wrap'
  }
}))

function MyCarousel() {
  const items = [
    {
      name: "xPhone",
      description: "Lots to love. Less to spend. \nStarting at $399.",
      imgSrc: require('../assets/iphone.png'),
      sequence: 'first',
      backgroundColor: '#111111'
    },
    {
      name: "Tablet",
      description: "Just the right amount of everything",
      imgSrc: require('../assets/tablet.png'),
      sequence: 'second',
      backgroundColor: '#fafafa'
    },
    {
      name: "Buy a Tablet or xPhone for college. Get arPods.",
      imgSrc: require('../assets/airpods.png'),
      sequence: 'third',
      backgroundColor: '#f1f1f3'
    }
  ];

  const classes = useStyle()

  return (
    <Carousel
      autoPlay={true}
      className={classes.container}
      IndicatorIcon={<div style={{height: 3, marginTop: 0}}/>}
      indicatorIconButtonProps={{
          style: {
            backgroundColor: 'grey',
            borderRadius: 0,
            marginLeft: 10,
            width: 70,
            zIndex: 99,
            borderRight: '70px solid grey',
            transition: 'border 2s'
          }
      }}
      activeIndicatorIconButtonProps={{
        style: {
          backgroundColor: 'whitesmoke',
          borderRadius: 0,
          marginLeft: 10,
          zIndex: 99,
          width: 70,
          borderRight: '0px solid grey',
        }
      }}
    >
      {
        items.map( (item, i) => <Item key={i} item={item} />)
      }
    </Carousel>
  )
}

// @ts-ignore
function Item(props) {
  const {name, description, imgSrc, sequence, backgroundColor} = props.item
  const classes = useStyle()

  return (
    <div className={classes.root}>
      <CardMedia
        component="img"
        image={imgSrc}
        className={classes.img}
      />
      <div className={sequence === 'first' ? classes.firstFont : classes.font}>
        <h1 className={classes.title}>{name}</h1>
        <p className={classes.description}>{description}</p>
      </div>
      <div style={{
  height: '20vh',
  backgroundColor: backgroundColor,
}}/>
    </div>
  )
}


export default MyCarousel;

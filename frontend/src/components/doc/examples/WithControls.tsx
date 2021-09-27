import React, { FC } from "react";
import { Carousel } from "../../Carousel";
import "./style.css";

const getSlides = () => {
  const slidesDescription = [
    {
      caption: "Nomadland",
      description:
        "A woman in her sixties, after losing everything in the Great Recession, embarks on a journey through the American West, living as a van-dwelling modern-day nomad",
      director: "Chloé Zhao",
      bgImg:
        "https://m.media-amazon.com/images/M/MV5BMDRiZWUxNmItNDU5Yy00ODNmLTk0M2ItZjQzZTA5OTJkZjkyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_FMjpg_UY720_.jpg",
    },
    {
      caption: "The Social Network",
      description:
        "As Harvard student Mark Zuckerberg creates the social networking site that would become known as Facebook, he is sued by the twins who claimed he stole their idea, and by the co-founder who was later squeezed out of the business.",
      director: "David Fincher",
      bgImg:
        "https://m.media-amazon.com/images/M/MV5BOGUyZDUxZjEtMmIzMC00MzlmLTg4MGItZWJmMzBhZjE0Mjc1XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_FMjpg_UX675_.jpg",
    },
    {
      caption: "So Long, My Son",
      description: "Two married couples adjust to the vast social and economic changes taking place in China from the 1980s to the present.",
      director: "Xiaoshuai Wang",
      bgImg:
        "https://m.media-amazon.com/images/M/MV5BMDQ2OTcwYTQtYmViZS00MjkzLWIwNTAtODUwMmJkYjZhMDE0XkEyXkFqcGdeQXVyNzI1NzMxNzM@._V1_FMjpg_UY720_.jpg",
    },
    {
      caption: "Yi Yi: A One and a Two...",
      description: "Each member of a middle-class Taipei family seeks to reconcile past and present relationships within their daily lives.",
      director: "Edward Yang",
      bgImg:
        "https://m.media-amazon.com/images/M/MV5BZDNkMGUyYzUtNjM0ZC00NDM2LWE5ZjEtMjliNzIxMmMzZThhXkEyXkFqcGdeQXVyMzAxNjg3MjQ@._V1_FMjpg_UX650_.jpg",
    },
    {
      caption: "In the Heat of the Sun",
      description: "The story of four teenagers in Beijing during the Cultural Revolution.",
      director: "Wen Jiang",
      bgImg:
        "https://m.media-amazon.com/images/M/MV5BN2ZiYTM5MDgtNWVkNy00OGM2LThkOWMtODI1MTJjODE1YTk0XkEyXkFqcGdeQXVyNjc3MjQzNTI@._V1_FMjpg_UX1045_.jpg",
    },
  ];
  return slidesDescription.map(
    ({ caption, description, director, bgImg }) => (
      <div className="w-full h-full flex flex-col">
        <div
          className="movie-card w-full h-full"
          style={{ backgroundImage: `url(${bgImg})`, backgroundSize: "cover" }}
        ></div>
        <div className="movie-info absolute px-6 invisible text-white ">
          <h3 className="text-2xl text-center">{caption}</h3>
          <div className="text-center text-base">
            <p>{description}</p>
          </div>
          <div className="text-center text-sm">{director}</div>
        </div>
      </div>
    )
  );
};

export const DemoWithControl = () => {
  return <Carousel control items={getSlides()} customCSS="movies" />;
};

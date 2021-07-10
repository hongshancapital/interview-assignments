import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { animationFrameScheduler, asyncScheduler, SchedulerAction, Subscription } from 'rxjs';

@Component({
  selector: 'app-carousel',
  templateUrl: './carousel.component.html',
  styleUrls: ['./carousel.component.css']
})
export class CarouselComponent implements OnInit, OnDestroy {

  private readonly transitionDuration: number = 3000;
  readonly slides: SlideConfig[] = [
    { imageUrl: 'assets/iphone.png', fgc: 'white', bgc: '#111111', imageScale: 1, titles: ['xPhone'], subs: ['Lots to love. Less to spend.', 'Starting at $399.'] } ,
    { imageUrl: 'assets/tablet.png', fgc: 'black', bgc: '#fafafa', imageScale: 2, titles: ['Tablet'], subs: ['Just the right amount of everything.'] },
    { imageUrl: 'assets/airpods.png', fgc: 'black', bgc: '#f1f1f3', imageScale: 3, titles: ['Buy a Tablet or xPhone for college.', 'Get arPods.'] }
  ];
  currentSlide: number = 0;
  private timer: Subscription | undefined;

  @ViewChild('slidesViewport', {static: true}) slidesViewport: ElementRef | undefined;

  constructor() { }

  ngOnInit(): void {
    this.startTimer();
  }

  ngOnDestroy() {
    this.timer?.unsubscribe();
  }

  goToSlide(slideIndex: number, restartTimer: boolean = true): void {
    this.currentSlide = slideIndex;
    animationFrameScheduler.schedule(() => {
      this.slidesViewport?.nativeElement.scroll({ top: 0, left: this.currentSlide * 1000, behavior: 'smooth'});
    });
    if (restartTimer) {
      this.startTimer();
    }
  }

  private startTimer(): void {
    this.timer?.unsubscribe();
    const self = this;
    this.timer = asyncScheduler.schedule(function (this: SchedulerAction<number>, state?: any) {
      self.goToSlide((self.currentSlide + 1) % self.slides.length, false);
      this.schedule(state, self.transitionDuration);
    }, this.transitionDuration);
  }

}

interface SlideConfig {
  imageUrl: string;
  imageScale: number;
  fgc: string;
  bgc: string;
  titles: string[];
  subs?: string[];
}

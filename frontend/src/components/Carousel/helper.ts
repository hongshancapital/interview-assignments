export type State = 'prev' | 'current' | 'next';

export function getTranslateX(state: State): string {
  switch (state) {
    case 'prev':
      return '-100%';
    case 'next':
      return '100%';
    default:
      return '0';
  }
}

export function indexToState(index: number, currentIndex: number): State {
  if (index < currentIndex) {
    return 'prev';
  }

  if (index > currentIndex) {
    return 'next';
  }

  return 'current';
}


test('', () => {});

export function testTranslate(content: Element | null, index: number) {
  expect(content).toBeDefined();
  content && expect(content.getAttribute('style')).toMatch(new RegExp(`transform:([^;]*)translate\\((\\s*)-${index}00%,(\\s*)0(\\s*)\\)`));
}

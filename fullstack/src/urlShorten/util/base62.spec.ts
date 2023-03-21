import Base62Converter from './base62Converter';

test(`Decode 'ba' in base62 should return 62`, () => {
  console.log(
    Base62Converter.decode('baaaaaaa'),
    Base62Converter.decode('99999999'),
    ' 636363'
  );
  expect(Base62Converter.decode('ba')).toBe(62);
});

test(`Encode 1314520 in base62 should return 'fF76'`, () => {
  expect(Base62Converter.encode(1314520)).toBe('fF76');
});

# Copyright (c) 2013 Simplistix Ltd
#
# This Software is released under the MIT License:
# http://www.opensource.org/licenses/mit-license.html
# See license.txt for more details.
from datetime import datetime, time
from os import path
from unittest import TestCase

from testfixtures import compare, ShouldRaise

from xlutils.view import View, Row, Col, CheckerView
from xlutils.tests.fixtures import test_files
from xlwt.compat import PY3


class Check(object):

    def _check(self, view, *expected):
        actual = []
        for row in view:
            actual.append(tuple(row))
        compare(expected, tuple(actual))
    
class ViewTests(Check, TestCase):
        
    def test_string_index(self):
        self._check(
            View(path.join(test_files,'testall.xls'))['Sheet1'],
            (u'R0C0', u'R0C1'),
            (u'R1C0', u'R1C1'),
            (u'A merged cell', ''),
            ('', ''),
            ('', ''),
            (u'More merged cells', '')
            )

    def test_int_index(self):
        self._check(
            View(path.join(test_files,'testall.xls'))[0],
            (u'R0C0', u'R0C1'),
            (u'R1C0', u'R1C1'),
            (u'A merged cell', ''),
            ('', ''),
            ('', ''),
            (u'More merged cells', '')
            )

    def test_dates_and_times(self):
        self._check(
            View(path.join(test_files,'datetime.xls'))[0],
            (datetime(2012, 4, 13, 0, 0), ),
            (time(12, 54, 37), ),
            (datetime(2014, 2, 14, 4, 56, 23), ),
            )
        
    def test_xlsx(self):
        self._check(
            View(path.join(test_files,'sample.xlsx')),
            (),
            )
        
    def test_subclass(self):
        class MySheetView:
            def __init__(self, book, sheet):
                self.book, self.sheet = book, sheet
        class MyView(View):
            class_ = MySheetView
        view = MyView(path.join(test_files,'testall.xls'))
        sheet_view = view[0]
        self.assertTrue(isinstance(sheet_view, MySheetView))
        self.assertTrue(sheet_view.book is view.book)
        self.assertTrue(sheet_view.sheet is view.book.sheet_by_index(0))

    def test_passed_in_class(self):
        class MySheetView:
            def __init__(self, book, sheet):
                self.book, self.sheet = book, sheet
        view = View(path.join(test_files,'testall.xls'), class_=MySheetView)
        sheet_view = view[0]
        self.assertTrue(isinstance(sheet_view, MySheetView))
        self.assertTrue(sheet_view.book is view.book)
        self.assertTrue(sheet_view.sheet is view.book.sheet_by_index(0))

class SliceTests(Check, TestCase):

    def setUp(self):
        self.view = View(path.join(test_files,'testall.xls'))[0]

    def test_slice_int_ranges(self):
        self._check(
            self.view[1:2, 1:2],
            (u'R1C1',),
            )
        self._check(
            self.view[0:2, 0:1],
            (u'R0C0', ),
            (u'R1C0', ),
            )

    def test_slice_open_ranges(self):
        self._check(
            self.view[1:, 1:],
            (u'R1C1',),
            ('',),
            ('',),
            ('',),
            ('',)
            )
        self._check(
            self.view[:2, :2],
            (u'R0C0', u'R0C1'),
            (u'R1C0', u'R1C1'),
            )
        self._check(
            self.view[:, :],
            (u'R0C0', u'R0C1'),
            (u'R1C0', u'R1C1'),
            (u'A merged cell', ''),
            ('', ''),
            ('', ''),
            (u'More merged cells', '')
            )

    def test_slice_negative_ranges(self):
        self._check(
            self.view[-5:, -1:],
            (u'R1C1',),
            ('',),
            ('',),
            ('',),
            ('',)
            )
        self._check(
            self.view[:-4, :-1],
            (u'R0C0', ),
            (u'R1C0', ),
            )

    def test_slice_string_ranges(self):
        self._check(
            self.view[Row(1):Row(2), Col('A'):Col('B')],
            (u'R0C0', u'R0C1'),
            (u'R1C0', u'R1C1'),
            )

class CheckerViewTests(TestCase):
        
    def test_matches(self):
        CheckerView(path.join(test_files,'testall.xls'))['Sheet1'].compare(
            (u'R0C0', u'R0C1'),
            (u'R1C0', u'R1C1'),
            (u'A merged cell', ''),
            ('', ''),
            ('', ''),
            (u'More merged cells', '')
            )

        
    def test_does_not_match(self):
        with ShouldRaise(AssertionError) as s:
            CheckerView(path.join(test_files,'testall.xls'))['Sheet1'].compare(
                (u'R0C0', u'R0C1'),
                (u'R1C0', u'R1C1'),
                (u'A merged cell', ''),
                ('', ''),
                ('', ''),
                (u'More merged cells', 'XX')
                )

        if PY3:
            expected="""\
sequence not as expected:

same:
(('R0C0', 'R0C1'), ('R1C0', 'R1C1'), ('A merged cell', ''), ('', ''), ('', ''))

expected:
(('More merged cells', 'XX'),)

actual:
(('More merged cells', ''),)

While comparing [5]: sequence not as expected:

same:
('More merged cells',)

expected:
('XX',)

actual:
('',)

While comparing [5][1]: 'XX' (expected) != '' (actual)"""
        else:
            expected='''\
sequence not as expected:

same:
((u'R0C0', u'R0C1'),
 (u'R1C0', u'R1C1'),
 (u'A merged cell', ''),
 ('', ''),
 ('', ''))

expected:
((u'More merged cells', 'XX'),)

actual:
((u'More merged cells', u''),)

While comparing [5]: sequence not as expected:

same:
(u'More merged cells',)

expected:
('XX',)

actual:
(u'',)'''

        compare(expected, actual=str(s.raised))

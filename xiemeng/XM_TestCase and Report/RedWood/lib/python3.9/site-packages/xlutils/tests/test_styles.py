# Copyright (c) 2009 Simplistix Ltd
#
# This Software is released under the MIT License:
# http://www.opensource.org/licenses/mit-license.html
# See license.txt for more details.
from mock import Mock
from testfixtures import ShouldRaise
from unittest import TestSuite,TestCase,makeSuite
from xlutils.styles import Styles

class TestStyles(TestCase):

    def setUp(self):
        self.wb = Mock()
        self.wb.style_name_map = {
            '':(0,0),
            'Normal':(1,0),
            }
        xf0 = Mock()
        xf0.is_style = True
        xf0.parent_style_index=4095
        xf1 = Mock()
        xf1.is_style = False
        xf1.parent_style_index=0
        self.wb.xf_list = [xf0,xf1]
        
    def test_multiple_names_for_xfi_okay(self):
        # setup our mock workbooks
        self.wb.style_name_map = {
            '':(0,0),
            'Normal':(1,0),
            }
        
        # process it into styles
        s = Styles(self.wb)

        # now lookup a "cell" with xfi 0
        cell = Mock()
        cell.xf_index = 1
        self.assertEqual(s[cell].name,'Normal')
        
    def test_multiple_names_for_xfi_bad_1(self):
        self.wb.style_name_map = {
            'A':(0,0),
            'B':(0,0),
            }
        with ShouldRaise(AssertionError()):
            Styles(self.wb)

def test_suite():
    return TestSuite((
        makeSuite(TestStyles),
        ))

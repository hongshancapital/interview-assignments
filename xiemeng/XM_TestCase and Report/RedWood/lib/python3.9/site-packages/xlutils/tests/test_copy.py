# Copyright (c) 2009 Simplistix Ltd
#
# This Software is released under the MIT License:
# http://www.opensource.org/licenses/mit-license.html
# See license.txt for more details.

import os
from mock import Mock
from testfixtures import replace,compare,Comparison as C
from unittest import TestSuite,TestCase,makeSuite
from xlutils.copy import copy
from xlutils.filter import XLRDReader

class TestCopy(TestCase):

    @replace('xlutils.copy.XLWTWriter',Mock())
    @replace('xlutils.copy.process',Mock())
    def test_copy_xlrd(self,c,xlwtw):
        inwb = object()
        
        outwb = Mock()
        xlwtwi = Mock()
        xlwtwi.output=[('junk',outwb)]
        xlwtw.return_value=xlwtwi
        
        self.failUnless(copy(inwb) is outwb)
        
        self.assertEqual(len(c.call_args_list),1)
        args = c.call_args_list[0][0]
        self.assertEqual(len(args),2)
        
        r = args[0]
        self.failUnless(isinstance(r,XLRDReader))
        self.failUnless(r.wb is inwb)
        self.assertEqual(r.filename,'unknown.xls')

        w = args[1]
        self.failUnless(w is xlwtwi)

def test_suite():
    return TestSuite((
        makeSuite(TestCopy),
        ))

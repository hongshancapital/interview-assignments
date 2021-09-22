# Copyright (c) 2008 Simplistix Ltd
#
# This Software is released under the MIT License:
# http://www.opensource.org/licenses/mit-license.html
# See license.txt for more details.

import os
from mock import Mock
from ..compat import StringIO
from tempfile import TemporaryFile
from testfixtures import replace,tempdir
from unittest import TestSuite,TestCase,makeSuite
from xlutils.save import save
from xlutils.filter import XLRDReader,StreamWriter

class TestSave(TestCase):

    @tempdir()
    @replace('xlutils.save.process',Mock())
    def test_save_path(self,c,d):
        wb = object()
        path = os.path.join(d.path,'path.xls')
        
        save(wb,path)
        
        self.assertEqual(len(c.call_args_list),1)
        args = c.call_args_list[0][0]
        self.assertEqual(len(args),2)
        r = args[0]
        self.failUnless(isinstance(r,XLRDReader))
        self.failUnless(r.wb is wb)
        self.assertEqual(r.filename,'path.xls')
        w = args[1]
        self.failUnless(isinstance(w,StreamWriter))
        f = w.stream
        self.assertEqual(f.name, path)
        self.assertEqual(f.mode, 'wb')
        self.assertEqual(f.closed, True)
        
    @replace('xlutils.save.process',Mock())
    def test_save_stringio(self,c):
        wb = object()
        s = StringIO()
        
        save(wb,s)
        
        self.assertEqual(len(c.call_args_list),1)
        args = c.call_args_list[0][0]
        self.assertEqual(len(args),2)
        r = args[0]
        self.failUnless(isinstance(r,XLRDReader))
        self.failUnless(r.wb is wb)
        self.assertEqual(r.filename,'unknown.xls')
        w = args[1]
        self.failUnless(isinstance(w,StreamWriter))
        self.failUnless(w.stream is s)

    @replace('xlutils.save.process',Mock())
    def test_save_tempfile(self,c):
        wb = object()
        ef = TemporaryFile()
        
        save(wb,ef)
        
        self.assertEqual(len(c.call_args_list),1)
        args = c.call_args_list[0][0]
        self.assertEqual(len(args),2)
        r = args[0]
        self.failUnless(isinstance(r,XLRDReader))
        self.failUnless(r.wb is wb)
        self.assertEqual(r.filename,'unknown.xls')
        w = args[1]
        self.failUnless(isinstance(w,StreamWriter))
        af = w.stream
        self.failUnless(af is ef)
        self.assertEqual(ef.closed,False)
    
def test_suite():
    return TestSuite((
        makeSuite(TestSave),
        ))

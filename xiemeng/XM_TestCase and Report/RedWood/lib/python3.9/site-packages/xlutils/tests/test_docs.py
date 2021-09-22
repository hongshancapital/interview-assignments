# Copyright (c) 2008-2012 Simplistix Ltd
#
# This Software is released under the MIT License:
# http://www.opensource.org/licenses/mit-license.html
# See license.txt for more details.

from doctest import REPORT_NDIFF, ELLIPSIS
from glob import glob
from manuel import doctest
from manuel.testing import TestSuite
from testfixtures import LogCapture,TempDirectory
from os.path import dirname, join, pardir

from . import compat
from .fixtures import test_files

tests = glob(join(join(dirname(__file__), pardir, pardir), 'docs', '*.rst'))

options = REPORT_NDIFF|ELLIPSIS

def setUp(test):
    test.globs['test_files']=test_files
    test.globs['temp_dir']=TempDirectory().path
    test.globs['TempDirectory']=TempDirectory

def tearDown(test):
    TempDirectory.cleanup_all()
    LogCapture.uninstall_all()

def test_suite():
    m = doctest.Manuel(optionflags=REPORT_NDIFF | ELLIPSIS,
                       checker=compat.DocTestChecker())
    return TestSuite(m, *tests,
                     setUp=setUp,
                     tearDown=tearDown)

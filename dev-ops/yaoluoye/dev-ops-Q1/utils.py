import signal
import os
import sys
import stat


def handlesignal(sig, data):
    if sig == signal.SIGINT:
        print('exit by "Ctrl C"')
        sys.exit()
    elif sig == signal.SIGTERM:
        print('Exit by signal SIGTERM')
        sys.exit()

def getfiletype(targetfile):
    if os.path.ismount(targetfile):
        return 'mount'

    mode = os.lstat(targetfile).st_mode
    if stat.S_ISDIR(mode):
        return 'dir'
    elif stat.S_ISLNK(mode):
        return 'slink'
    elif stat.S_ISBLK(mode):
        return 'block'
    elif stat.S_ISCHR(mode):
        return 'char'
    elif stat.S_ISREG(mode):
        return 'regular'
    elif stat.S_ISFIFO(mode):
        return 'fifo'
    elif stat.S_ISSOCK(mode):
        return 'socket'
    else:
        return 'unknown'

def istext(targetfile):
    file_type = getfiletype(targetfile)
    if file_type != 'regular':
        return False

    textchars = bytearray({7, 8, 9, 10, 12, 13, 27} |
                          set(range(0x20, 0x100)) - {0x7f})
    is_binary_string = lambda bytes: bool(bytes.translate(None, textchars))
    if not is_binary_string(open(targetfile, 'rb').read(1024)):
        return True

def getfiles(filepath):
    """Get file list"""
    filelist = []

    file_type = getfiletype(filepath)
    if file_type == "dir":
        filelist = [os.path.join(filepath, p) for p in os.listdir(filepath) if os.path.isfile(os.path.join(filepath, p))]
    elif file_type == "regular":
        filelist = [filepath]

    return filelist
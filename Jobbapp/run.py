#!/usr/bin/env python

import os

package = "foobar"
app = "Run"
input = "test.shiftcfg"

cmd = "java "+package+"."+app+" < "+input

os.system(cmd)

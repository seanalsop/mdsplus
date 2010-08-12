from MDSplus import Device,Data,Action,Dispatch,Method, makeArray, Range, Signal, Window, Dimension
from tempfile import *
import acq200
import transport

from time import sleep
import os
import numpy

class DT196(Device):
    """
    D-Tacq ACQ196  96 channel transient recorder
    
    Methods:
    Add() - add a DT196B device to the tree open for edit
    Init(arg) - initialize the DT196B device 
                write setup parameters and waveforms to the device
    Store(arg) - store the data acquired by the device
    Help(arg) - Print this message
    
    Nodes:
    
    :HOSTIP - mdsip address of the host storing the data.  Usually 192.168.0.254:8106
     BOARDIP'- ip addres sof the card as a string something like 192.168.0.40
     COMMENT - user comment string
     DI[0-5] - time(s) of the signal on this internal wire (trig reference or clock reference)
            :wire - string specifying the source of this signal { 'fpga','mezz','rio','pxi','lemo', 'none }
            :bus  - string specifying the destination of this signal { 'fpga','rio','pxi', 'none }
    :ACTIVE_CHANS - number of active channels {32, 64, 96}
     INT_CLOCK - stored by module (representation of internal clock
     MASTER    - points to INT_CLOCK node - needed a node to fill into clock_src       
     TRIG_SRC - reference to DIn line used for trigger (DI3)
     TRIG_EDGE - string {rising, falling} 
     CLOCK_SRC - reference to line (DIn) used for clock or INT_CLOCK, or MASTER
     CLOCK_DIV - NOT CURRENTLY IMPLIMENTED 
     CLOCK_EDGE -  string {rising, falling}
     CLOCK_FREQ - frequency for internal clock
     PRE_TRIG - pre trigger samples MUST BE ZERO FOR NOW
     POST_TRIG - post trigger samples
     SEGMENTS - number of segments to store data in NOT IMPLIMENTED FOR NOW
     CLOCK - Filled in by store place for module to store clock information
     RANGES - place for module to store calibration information 
     STATUS_CMDS - array of shell commands to send to the module to record firmware version  etc
     BOARD_STATUS - place for module to store answers for STATUS_CMDS as signal
     INPUT_[01-96] - place for module to store data in volts (reference to INPUT_NN:RAW)
                  :RAW - place for module to store raw data in volts for each channel
                  START_IDX - first sample to store for this channel
                  END_IDX - last sample to store for this channel
                  INC - decimation factor for this channel
     INIT_ACTION - dispatching information for INIT
     STORE_ACTION - dispatching information for STORE
    """
    
    parts=[
        {'path':':NOE','type':'text','value':'192.168.0.254','options':('no_write_shot',)},
        {'path':':BOARD','type':'text','value':'192.168.0.0','options':('no_write_shot',)},
        {'path':':COMMENT','type':'text'},
        ]

    for i in range(6):
        parts.append({'path':':DI%1.1d'%(i,),'type':'axis','options':('no_write_shot',)})
        parts.append({'path':':DI%1.1d:BUS'%(i,),'type':'text','options':('no_write_shot',)})
        parts.append({'path':':DI%1.1d:WIRE'%(i,),'type':'text','options':('no_write_shot',)})

    parts2=[
        {'path':':CLOCK_SRC','type':'numeric','valueExpr':'head.int_clock','options':('no_write_shot',)},
        {'path':':CLOCK_DIV','type':'numeric','value':1,'options':('no_write_shot',)},
        {'path':':DAQ_MEM','type':'numeric','value':512,'options':('no_write_shot',)},
        {'path':':ACTIVE_CHANS','type':'numeric','value':96,'options':('no_write_shot',)},
        {'path':':TRIG_SRC','type':'text','value':'DI3','options':('no_write_shot',)},
        {'path':':POST_TRIG','type':'numeric','value':128,'options':('no_write_shot',)},
        {'path':':PRE_TRIG','type':'numeric','value':0,'options':('no_write_shot',)},
        ]
    parts.extend(parts2)
    del parts2
    for i in range(96):
        parts.append({'path':':INPUT_%2.2d'%(i+1,),'type':'signal','options':('no_write_model','write_once',)})
        parts.append({'path':':INPUT_%2.2d:STARTIDX'%(i+1,),'type':'NUMERIC', 'options':('no_write_shot')})
        parts.append({'path':':INPUT_%2.2d:ENDIDX'%(i+1,),'type':'NUMERIC', 'options':('no_write_shot')})
        parts.append({'path':':INPUT_%2.2d:INC'%(i+1,),'type':'NUMERIC', 'options':('no_write_shot')})
        parts.append({'path':':INPUT_%2.2d:FILTER_COEFS'%(i+1,),'type':'NUMERIC', 'options':('no_write_shot')})

    parts.append({'path':':INIT_ACTION','type':'action',
                  'valueExpr':"Action(Dispatch('CAMAC_SERVER','INIT',50,None),Method(None,'INIT',head))",
                  'options':('no_write_shot',)})
    parts.append({'path':':STORE_ACTION','type':'action',
                  'valueExpr':"Action(Dispatch('CAMAC_SERVER','STORE',50,None),Method(None,'STORE',head))",
                  'options':('no_write_shot',)})
    
    trig_sources=[ 'DI0',
                   'DI1',
                   'DI2',
                   'DI3',
                   'DI4',
                   'DI5',
                   ]
    clock_sources = trig_sources
    clock_sources.append('INT_CLOCK')
    clock_sources.append('MASTER')
    
    wires = [ 'fpga','mezz','rio','pxi','lemo', 'none', 'fpga pxi']
    
    del i
    
    def check(self, expression, message):
        try:
            ans = eval(expression)
            return ans
        except:
            raise Exception, message
        
    def initftp(self, arg):
        """
        Initialize the device
        Send parameters
        Arm hardware
        """

        debug=os.getenv("DEBUG_DEVICES")
        try:
	    boardip=str(self.boardip.record)
        except:
	    try:
		boardip = Dt200WriteMaster(hostboard, "/sbin/ifconfig eth0 | grep 'inet addr' | awk -F: '{print $2}' | awk '{print $1}'", 1)
	    except:
		raise Exception, "could not get board ip from either tree or hub"

	try:
            UUT = acq200.Acq200(transport.factory(boardip))
            active_chans = self.check("int(self.active_chans)", "Must specify active chans as int in (32,64,96)")
            if active_chans not in (32,64,96) :
                print "active chans must be in (32, 64, 96 )"
                active_chans = 96
            trig_src=self.check("str(self.trig_src)", "Could not read trigger source")
            if not trig_src in self.trig_sources:
                raise Exception, "Trig_src must be in %s" % str(self.trig_sources)
            clock_src=self.check("str(self.clock_src)", "Could not read trigger source")
            if not clock_src in self.clock_sources:
                raise Exception, "clock_src must be in %s" % str(self.clock_sources)
            pre_trig=self.check('int(self.pre_trig.data()*1024)', "Must specify pre trigger samples")
            post_trig=self.check('int(self.post_trig.data()*1024)', "Must specify post trigger samples")
            if clock_src == "INT" or clock_src == "MASTER":
		clock_freq = self.check('int(self.clock_div)', "Must specify clock frequency in clock_div node for internal clock")
            else :
		try:
		    clock_div = int(self.clock_div)
		except:
		    clock_div = 1

            UUT.set_abort()
            UUT.clear_routes()
            
            for i in range(6):
                line = 'd%1.1d' % i
                try:
                    wire = eval('str(self.di%1.1d_wire.record)' %i)
                    if wire not in self.wires :
                        print "DI%d:wire must be in %s" % (i, str(self.wires), )
                        wire = 'fpga'
                except:
                    wire = 'fpga'
                try:
                    bus = eval('str(self.di%1.1d_bus.record)' % i)
                    if bus not in self.wires :
                        print "DI%d:bus must be in %s" % (i, str(self.wires),)
                        bus = ''
                except:
                    bus = ''
                UUT.set_route(line, 'in %s out %s' % (wire, bus,))
            UUT.setChannelCount(active_chans)

            if clock_src == 'INT_CLOCK' or clock_src == 'MASTER' :
                UUT.uut.acqcmd("setInternalClock %d" % clock_freq)
		if clock_src == 'MASTER' :
		    UUT.uut.acqcmd('-- setDIO -1-----')
            else:
		if (clock_div != 1) :        
		    UUT.uut.acqcmd("setExternalClock %s %d D02" % (clock_src, clock_div,))
#
# the following is not generic
# the clock is output on d2 and comes from DI0
#
		    UUT.set_route('d2', 'in fpga out pxi')
		    UUT.uut.acqcmd('-- setDIO --1-----')
                    UUT.uut.acq2sh('set.ext_clk DI0')

		else :
                    UUT.uut.acqcmd("setExternalClock %s" % clock_src)

            UUT.setPrePostMode(pre_trig, post_trig)
#            mask = UUT.uut.acqcmd('getChannelMask').split('=')[-1]
#
# now create the post_shot ftp command file
#
            (fd,fname) = mkstemp('.sh')
            f=open(fname, 'w')
            f.write("#!/bin/sh\n")
	    f.write("storeftp.sh %s %d %s\n" % (self.comment.tree.name, self.comment.tree.shot, self.path,))
	    f.close
            cmd = 'chmod a+x %s;curl -s -T %s ftp://%s/%s' %(fname, fname, boardip, 'post_shot.sh')
            pipe = os.popen(cmd)
            pipe.close()
	    UUT.uut.acq2sh('mv /home/ftp/post_shot.sh /etc/postshot.d/')
            UUT.set_arm() 
            return  1

        except Exception,e:
            print "%s\n" % (str(e),)
            return 0

    INITFTP=initftp
        
    def getVins(self, UUT):
        vin1 = UUT.uut.acq2sh('get.vin 1:32')
        vin2 = UUT.uut.acq2sh('get.vin 33:64')
        vin3 = UUT.uut.acq2sh('get.vin 65:96')
        ans = eval('makeArray([%s, %s, %s])' % (vin1, vin2, vin3,))
        return ans

        
    def getInternalClock(self, UUT):
        clock_str = UUT.uut.acqcmd('getInternalClock').split()[0].split('=')[1]
        print "clock_str is -%s-" % clock_str
        return eval('int(%s)' % clock_str)
        
        help(DT196B)
        return 1

    HELP=help


import unittest
from project import SystemperformanceAlert
from project import DataUtil
from project import CoapClientConnector
from project import GeolocationAdaptor

"""
Test class for all requisite Project functionality.

Instructions:
1) Rename 'testSomething()' method such that 'Something' is specific to your needs; add others as needed, beginning each method with 'test...()'.
2) Add the '@Test' annotation to each new 'test...()' method you add.
3) Import the relevant modules and classes to support your tests.
4) Run this class as unit test app.
5) Include a screen shot of the report when you submit your assignment.

Please note: While some example test cases may be provided, you must write your own for the class.
"""

class ProjectTest(unittest.TestCase):

	"""
	Use this to setup your tests. This is where you may want to load configuration
	information (if needed), initialize class-scoped variables, create class-scoped
	instances of complex objects, initialize any requisite connections, etc.
	"""
	def setUp(self):
		pass

	"""
	Use this to tear down any allocated resources after your tests are complete. This
	is where you may want to release connections, zero out any long-term data, etc.
	"""
	def tearDown(self):
		pass

	"""
	Test If CPU Utilization value is Correct 
	"""
	def testCPU_util_isvalid(self):
		sysCpuUtilTask = SystemperformanceAlert.SystemperformanceAlert()
		cpuUtil = sysCpuUtilTask.getCPUPercentage()		
		self.assertTrue(cpuUtil >= 0 and cpuUtil < 100),"Bad CPU Utilization Value! " + str(cpuUtil)

	"""
	Test If CPU Utilization value is Incorrect 
	"""		
	def testCPU_util_isInvalid(self):
		sysCpuUtilTask = SystemperformanceAlert.SystemperformanceAlert()
		cpuUtil = sysCpuUtilTask.getCPUPercentage()		
		self.assertFalse(cpuUtil < 0 or cpuUtil > 100),"Bad CPU Utilization Value!" + str(cpuUtil)
		self.assertFalse(cpuUtil == 100),"Bad CPU Utilization Value!" + str(cpuUtil)

	"""
	Test If Memory Utilization value is Correct 
	"""	
	def testMemory_util_isvalid(self):
		sysMemUtilTask = SystemperformanceAlert.SystemperformanceAlert()
		memUtil = sysMemUtilTask.getMemoryPercentage()		
		self.assertTrue(memUtil >= 0 and memUtil < 100),"Bad memory Utilization!" + str(memUtil)

	"""
	Test If Memory Utilization value is Incorrect 
	"""			
	def testMemory_util_isInvalid(self):
		sysMemUtilTask = SystemperformanceAlert.SystemperformanceAlert()
		memUtil = sysMemUtilTask.getMemoryPercentage()		
		self.assertFalse(memUtil < 0 or memUtil > 100),"Bad memory Utilization Value!" + str(memUtil)
		self.assertFalse(memUtil == 100),"Bad memory Utilization Value!" + str(memUtil)

	"""
	 Test for valid target email address 
	"""
	def testValidtargetemail(self):	
		adaptor = SystemperformanceAlert.SystemperformanceAlert()
		email = 'somanwita21@gmail.com';
		validity = adaptor.isvalid(email);		
		if (validity == True):
			pass		

	"""
	 Test for valid target email address 
	"""
	def testInvalidtargetemail(self):	
		adaptor = SystemperformanceAlert.SystemperformanceAlert()
		email = 'foobar';
		validity = adaptor.isvalid(email);	
		if (validity == False):
			pass	
		
			
if __name__ == "__main__":
	#import sys;sys.argv = ['', 'Test.testName']
	unittest.main()
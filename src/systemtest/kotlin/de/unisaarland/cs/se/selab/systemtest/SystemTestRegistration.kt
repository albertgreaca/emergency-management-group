package de.unisaarland.cs.se.selab.systemtest

import de.unisaarland.cs.se.selab.systemtest.basictests.EmergencySimpleTest
import de.unisaarland.cs.se.selab.systemtest.basictests.ExampleTest
import de.unisaarland.cs.se.selab.systemtest.basictests.ReroutingTest
import de.unisaarland.cs.se.selab.systemtest.failedparser.FailedParser1
import de.unisaarland.cs.se.selab.systemtest.failedparser.FailedParser10
import de.unisaarland.cs.se.selab.systemtest.failedparser.FailedParser11
import de.unisaarland.cs.se.selab.systemtest.failedparser.FailedParser12
import de.unisaarland.cs.se.selab.systemtest.failedparser.FailedParser13
import de.unisaarland.cs.se.selab.systemtest.failedparser.FailedParser14
import de.unisaarland.cs.se.selab.systemtest.failedparser.FailedParser15
import de.unisaarland.cs.se.selab.systemtest.failedparser.FailedParser16
import de.unisaarland.cs.se.selab.systemtest.failedparser.FailedParser17
import de.unisaarland.cs.se.selab.systemtest.failedparser.FailedParser2
import de.unisaarland.cs.se.selab.systemtest.failedparser.FailedParser3
import de.unisaarland.cs.se.selab.systemtest.failedparser.FailedParser4
import de.unisaarland.cs.se.selab.systemtest.failedparser.FailedParser5
import de.unisaarland.cs.se.selab.systemtest.failedparser.FailedParser6
import de.unisaarland.cs.se.selab.systemtest.failedparser.FailedParser7
import de.unisaarland.cs.se.selab.systemtest.failedparser.FailedParser8
import de.unisaarland.cs.se.selab.systemtest.failedparser.FailedParser9
import de.unisaarland.cs.se.selab.systemtest.runner.SystemTestManager

object SystemTestRegistration {
    fun registerSystemTests(manager: SystemTestManager) {
        manager.registerTest(ExampleTest())
        manager.registerTest(EmergencySimpleTest())
        manager.registerTest(ReroutingTest())
        manager.registerTest(FailedParser1())
        manager.registerTest(FailedParser2())
        manager.registerTest(FailedParser3())
        manager.registerTest(FailedParser4())
        manager.registerTest(FailedParser5())
        manager.registerTest(FailedParser6())
        manager.registerTest(FailedParser7())
        manager.registerTest(FailedParser8())
        manager.registerTest(FailedParser9())
        manager.registerTest(FailedParser10())
        manager.registerTest(FailedParser11())
        manager.registerTest(FailedParser12())
        manager.registerTest(FailedParser13())
        manager.registerTest(FailedParser14())
        manager.registerTest(FailedParser15())
        manager.registerTest(FailedParser16())
        manager.registerTest(FailedParser17())
    }
}

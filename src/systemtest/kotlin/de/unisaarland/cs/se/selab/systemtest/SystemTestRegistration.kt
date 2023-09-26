package de.unisaarland.cs.se.selab.systemtest
import de.unisaarland.cs.se.selab.systemtest.basictests.EmergencySimpleTest
import de.unisaarland.cs.se.selab.systemtest.basictests.ExampleTest
import de.unisaarland.cs.se.selab.systemtest.basictests.ReroutingTest
import de.unisaarland.cs.se.selab.systemtest.failedparser.*
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
        manager.registerTest(FailedParserSyntax1())
        manager.registerTest(FailedParserSyntax2())
        manager.registerTest(FailedParserSyntax3())
        manager.registerTest(FailedParserSyntax4())
        manager.registerTest(FailedParserSyntax5())
    }
}

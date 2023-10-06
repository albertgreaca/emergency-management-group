package de.unisaarland.cs.se.selab.systemtest

import FailedParserConfig3Semantics1
import de.unisaarland.cs.se.selab.systemtest.basictests.*
import de.unisaarland.cs.se.selab.systemtest.complicated.DoubleRealloc
import de.unisaarland.cs.se.selab.systemtest.complicated.ManyEvents
import de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.*
import de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.FailedParser1
import de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.baseWrongProperties.FireWithDoctors
import de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.baseWrongProperties.FireWithDogs
import de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.baseWrongProperties.HospitalWithDogs
import de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.baseWrongProperties.PoliceWithDoctors
import de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.vehiclesWrongBase.*
import de.unisaarland.cs.se.selab.systemtest.failedconfig3parser.semantics.*
import de.unisaarland.cs.se.selab.systemtest.failedconfig3parser.semantics.additionalattributes.*
import de.unisaarland.cs.se.selab.systemtest.failedconfig3parser.syntax.*
import de.unisaarland.cs.se.selab.systemtest.failedmapparser.*
import de.unisaarland.cs.se.selab.systemtest.failedmapparser.FailedParser10
import de.unisaarland.cs.se.selab.systemtest.failedmapparser.FailedParser11
import de.unisaarland.cs.se.selab.systemtest.failedmapparser.FailedParser12
import de.unisaarland.cs.se.selab.systemtest.failedmapparser.FailedParser13
import de.unisaarland.cs.se.selab.systemtest.failedmapparser.FailedParser14
import de.unisaarland.cs.se.selab.systemtest.failedmapparser.FailedParser15
import de.unisaarland.cs.se.selab.systemtest.failedmapparser.FailedParser16
import de.unisaarland.cs.se.selab.systemtest.failedmapparser.FailedParser17
import de.unisaarland.cs.se.selab.systemtest.failedmapparser.FailedParser2
import de.unisaarland.cs.se.selab.systemtest.failedmapparser.FailedParser3
import de.unisaarland.cs.se.selab.systemtest.failedmapparser.FailedParser4
import de.unisaarland.cs.se.selab.systemtest.failedmapparser.FailedParser5
import de.unisaarland.cs.se.selab.systemtest.failedmapparser.FailedParser6
import de.unisaarland.cs.se.selab.systemtest.failedmapparser.FailedParser7
import de.unisaarland.cs.se.selab.systemtest.failedmapparser.FailedParser8
import de.unisaarland.cs.se.selab.systemtest.failedmapparser.FailedParser9
import de.unisaarland.cs.se.selab.systemtest.failedmapparser.testguesses.*
import de.unisaarland.cs.se.selab.systemtest.runner.SystemTestManager
import de.unisaarland.cs.se.selab.systemtest.vehicleattributes.*

object SystemTestRegistration {
    private fun registerSystemTest(manager: SystemTestManager) {
        manager.registerTest(ExampleTest())
        manager.registerTest(ReroutingTest())
        manager.registerTest(RequestTest())
        manager.registerTest(RequestTestFailed())
        manager.registerTest(RequestTestPartial())
        manager.registerTest(EventIdTest())
        manager.registerTest(RoadClosureTest())
        manager.registerTest(RushHour1Test())
        manager.registerTest(ManyEvents())
        manager.registerTest(IDontLikeHeights())
        manager.registerTest(Reallocation())
        manager.registerTest(ReallocationBackTest2())
        manager.registerTest(NotEnoughStaffTest())
        manager.registerTest(SmartAllocationWater())
        manager.registerTest(HeightChangeTest())
        manager.registerTest(MultipleReallocationsTest())
        manager.registerTest(EventIDCollision())
        manager.registerTest(FinishingTooLate())
        manager.registerTest(MultipleReallocationsTest())
        manager.registerTest(EventIDCollision())
        manager.registerTest(NotInTimeTest())
        manager.registerTest(RequestEverywhere())
        manager.registerTest(DijTieSimple())
        manager.registerTest(LessDogsThanK9Cars())
        manager.registerTest(EmergencySameTickTest())
        manager.registerTest(DoubleRealloc())
        manager.registerTest(DijTieNodesSimple())
        manager.registerTest(ArrivalTickFailWithRequest())
        manager.registerTest(RequestNotReallocate())
        manager.registerTest(LessDoctorsThanDoctorCars())
        manager.registerTest(RequestAgainStaff())
        manager.registerTest(OrderedSeverity())
        manager.registerTest(VehUnavPp())
        manager.registerTest(AllocationWithEvent())
        manager.registerTest(ReallocUnav())
    }

    private fun notPassingReference(manager: SystemTestManager) {
        manager.registerTest(ReallocUnav())
        manager.registerTest(LessLadderTest())
        manager.registerTest(OneWay())
    }

    fun registerSystemTestsReferenceImpl(manager: SystemTestManager) {
        notPassingReference(manager)
        registerSystemTest(manager)
        // registerSystemTestsMutantValidation(manager)
        // registerSystemTestsMutantSimulation(manager)
    }

    fun registerSystemTestsMutantValidation(manager: SystemTestManager) {
        manager.registerTest(CountyRoadDifferentName())
        manager.registerTest(VehicleCapGRBaseCap())
        registerMapParserSyntaxFail1(manager)
        registerMapParserSyntaxFail2(manager)
        registerMapParserSemanticFail1(manager)
        config2(manager)
        // registerConfig3Syntax(manager)
        registerConfig3Semantics(manager)
        manager.registerTest(HeightNegative())
        manager.registerTest(HeightZero())
        manager.registerTest(NoMain())
        manager.registerTest(NoRoads())
        manager.registerTest(RoadsWithoutVertex())
        manager.registerTest(SameRoadName())
        manager.registerTest(SameRoadVertex())
        manager.registerTest(SameVertexID())
        manager.registerTest(Tunnel4())
        manager.registerTest(UnEqualWeight())
        manager.registerTest(NoSideStreet())
        manager.registerTest(RoadToItself())
        manager.registerTest(FromVertexDiffrentVillage())
        manager.registerTest(CountyVillageName())
        registerVehiclesWrongBase(manager)
        registerVehicleWrongProperties(manager)
        manager.registerTest(FireWithDogs())
        manager.registerTest(FireWithDoctors())
        manager.registerTest(PoliceWithDoctors())
        manager.registerTest(HospitalWithDogs())
        registerEventWrongProperties(manager)
        manager.registerTest(FailedParser51())
        manager.registerTest(BaseSameLocation())
    }

    private fun registerEventWrongProperties(manager: SystemTestManager) {
        manager.registerTest(ConstructionSiteHasRoadTypes())
        manager.registerTest(ConstructionSiteHasVehicleID())
        manager.registerTest(RoadClosureHasFactor())
        manager.registerTest(RoadClosureHasOneWayStreet())
        manager.registerTest(RoadClosureHasRoadTypes())
        manager.registerTest(RoadClosureHasVehicleID())
        manager.registerTest(RushHourHasOneWayStreet())
        manager.registerTest(RushHourHasSource())
        manager.registerTest(RushHourHasTarget())
        manager.registerTest(RushHourHasVehicleID())
        manager.registerTest(TrafficJamHasOneWayStreet())
        manager.registerTest(TrafficJamHasRoadTypes())
        manager.registerTest(TrafficJamHasVehicleID())
        manager.registerTest(VehicleUnavailableHasFactor())
        manager.registerTest(VehicleUnavailableHasOneWayStreet())
        manager.registerTest(VehicleUnavailableHasSource())
        manager.registerTest(VehicleUnavailableHasTarget())
        manager.registerTest(VehicleUnavailableHasRoadTypes())
    }

    private fun registerVehicleWrongProperties(manager: SystemTestManager) {
        manager.registerTest(AmbulanceCrimeTest())
        manager.registerTest(AmbulanceLadderTest())
        manager.registerTest(AmbulanceWater())
        manager.registerTest(CarLadderTest())
        manager.registerTest(CarWaterTest())
        manager.registerTest(EDCCriminalTest())
        manager.registerTest(EDCLadderTest())
        manager.registerTest(EDCWaterTest())
        manager.registerTest(K9CriminalTest())
        manager.registerTest(K9LadderTest())
        manager.registerTest(K9WaterTest())
        manager.registerTest(LadderCriminalTest())
        manager.registerTest(LadderWaterTest())
        manager.registerTest(MotorCycleTest())
        manager.registerTest(MotorcycleLadderTest())
        manager.registerTest(MotorcycleWaterTest())
        manager.registerTest(TechnicalCriminalTest())
        manager.registerTest(TechnicalLadderTest())
        manager.registerTest(TechnicalWaterTest())
        manager.registerTest(TransportCriminalTest())
        manager.registerTest(TransportLadderTest())
        manager.registerTest(TransportWaterTest())
        manager.registerTest(WaterCriminalTest())
        manager.registerTest(WaterLadderTest())
        manager.registerTest(EmergencySimpleTest())
        manager.registerTest(ReallocationBackTest())
    }

    private fun registerVehiclesWrongBase(manager: SystemTestManager) {
        manager.registerTest(AMatFire())
        manager.registerTest(AMatPolice())
        manager.registerTest(EDatFire())
        manager.registerTest(EDatPolice())
        manager.registerTest(FTWatHostpital())
        manager.registerTest(FTWatPolice())
        manager.registerTest(FTTatHostpital())
        manager.registerTest(FTTatPolice())
        manager.registerTest(FTPatHostpital())
        manager.registerTest(FTPatPolice())
        manager.registerTest(FTLatHostpital())
        manager.registerTest(FTLatPolice())
        manager.registerTest(PCatFire())
        manager.registerTest(PCatHostpital())
        manager.registerTest(PMatHostpital())
        manager.registerTest(PMatFire())
        manager.registerTest(K9atFire())
        manager.registerTest(K9atHostpital())
    }

    fun registerConfig3Syntax(manager: SystemTestManager) {
        manager.registerTest(Config3SyntaxFailed1())
        manager.registerTest(Config3SyntaxFailed2())
        manager.registerTest(Config3SyntaxFailed3())
        manager.registerTest(Config3SyntaxFailed4())
        // manager.registerTest(Config3SyntaxFailed5())
        manager.registerTest(Config3SyntaxFailed6())
        manager.registerTest(Config3SyntaxFailed7())
        manager.registerTest(Config3SyntaxFailed8())
        manager.registerTest(Config3SyntaxFailed9())
        // manager.registerTest(Config3SyntaxFailed10())
        manager.registerTest(Config3SyntaxFailed11())
        manager.registerTest(Config3SyntaxFailed12())
        manager.registerTest(Config3SyntaxFailed13())
        manager.registerTest(Config3SyntaxFailed14())
        manager.registerTest(Config3SyntaxFailed15())
        manager.registerTest(Config3SyntaxFailed16())
        // manager.registerTest(Config3SyntaxFailed17())
        manager.registerTest(Config3SyntaxFailed18())
        manager.registerTest(Config3SyntaxFailed19())
        // manager.registerTest(Config3SyntaxFailed20())
        manager.registerTest(Config3SyntaxFailed21())
        manager.registerTest(Config3SyntaxFailed22())
        manager.registerTest(Config3SyntaxFailed23())
        manager.registerTest(Config3SyntaxFailed24())
        manager.registerTest(Config3SyntaxFailed25())
    }

    fun registerConfig3Semantics(manager: SystemTestManager) {
        manager.registerTest(FailedParserConfig3Semantics1())
        manager.registerTest(FailedParserConfig3Semantics2())
        manager.registerTest(FailedParserConfig3Semantics3())
        manager.registerTest(FailedParserConfig3Semantics4())
        manager.registerTest(FailedParserConfig3Semantics5())
        manager.registerTest(FailedParserConfig3Semantics6())
        manager.registerTest(FailedParserConfig3Semantics7())
        manager.registerTest(FailedParserConfig3Semantics8())
        manager.registerTest(FailedParserConfig3Semantics9())
        manager.registerTest(FailedParserConfig3Semantics10())
        manager.registerTest(FailedParserConfig3Semantics11())
        manager.registerTest(FailedParserConfig3Semantics12())
        manager.registerTest(FailedParserConfig3Semantics13())
        manager.registerTest(FailedParserConfig3Semantics14())
        manager.registerTest(FailedParserConfig3Semantics15())
        manager.registerTest(FailedParserConfig3Semantics16())
    }

    fun registerSystemTestsMutantSimulation(manager: SystemTestManager) {
        registerSystemTest(manager)
    }

    private fun registerMapParserSemanticFail1(manager: SystemTestManager) {
        manager.registerTest(de.unisaarland.cs.se.selab.systemtest.failedmapparser.FailedParser1())
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

    private fun registerMapParserSyntaxFail1(manager: SystemTestManager) {
        manager.registerTest(FailedParserSyntax1())
        manager.registerTest(FailedParserSyntax2())
        manager.registerTest(FailedParserSyntax3())
        manager.registerTest(FailedParserSyntax4())
        manager.registerTest(FailedParserSyntax5())
        manager.registerTest(FailedParserSyntax6())
        manager.registerTest(FailedParserSyntax7())
        manager.registerTest(FailedParserSyntax8())
        manager.registerTest(FailedParserSyntax9())
        manager.registerTest(FailedParserSyntax10())
        manager.registerTest(FailedParserSyntax11())
        manager.registerTest(FailedParserSyntax12())
        manager.registerTest(FailedParserSyntax13())
        manager.registerTest(FailedParserSyntax14())
        manager.registerTest(FailedParserSyntax15())
        manager.registerTest(FailedParserSyntax16())
        manager.registerTest(FailedParserSyntax17())
        manager.registerTest(FailedParserSyntax18())
        manager.registerTest(FailedParserSyntax19())
        manager.registerTest(FailedParserSyntax20())
        manager.registerTest(FailedParserSyntax21())
        manager.registerTest(FailedParserSyntax22())
        manager.registerTest(FailedParserSyntax23())
        manager.registerTest(FailedParserSyntax24())
        manager.registerTest(FailedParserSyntax25())
        manager.registerTest(FailedParserSyntax26())
        manager.registerTest(FailedParserSyntax27())
        manager.registerTest(FailedParserSyntax28())
        manager.registerTest(FailedParserSyntax29())
        manager.registerTest(FailedParserSyntax30())
        manager.registerTest(FailedParserSyntax31())
        manager.registerTest(FailedParserSyntax32())
        manager.registerTest(FailedParserSyntax33())
        manager.registerTest(FailedParserSyntax34())
        manager.registerTest(FailedParserSyntax35())
        manager.registerTest(FailedParserSyntax36())
    }

    private fun registerMapParserSyntaxFail2(manager: SystemTestManager) {
        manager.registerTest(FailedParserSyntax37())
        manager.registerTest(FailedParserSyntax38())
        manager.registerTest(FailedParserSyntax39())
        manager.registerTest(FailedParserSyntax40())
        manager.registerTest(FailedParserSyntax41())
        manager.registerTest(FailedParserSyntax42())
        manager.registerTest(FailedParserSyntax43())
        manager.registerTest(FailedParserSyntax44())
        manager.registerTest(FailedParserSyntax45())
        manager.registerTest(FailedParserSyntax46())
        manager.registerTest(FailedParserSyntax47())
        manager.registerTest(FailedParserSyntax48())
        manager.registerTest(FailedParserSyntax49())
        manager.registerTest(FailedParserSyntax50())
        manager.registerTest(FailedParserSyntax51())
        manager.registerTest(FailedParserSyntax52())
        manager.registerTest(FailedParserSyntax53())
        manager.registerTest(FailedParserSyntax54())
        manager.registerTest(FailedParserSyntax55())
        manager.registerTest(FailedParserSyntax56())
        manager.registerTest(FailedParserSyntax57())
        manager.registerTest(FailedParserSyntax58())
        manager.registerTest(FailedParserSyntax59())
        manager.registerTest(FailedParserSyntax60())
        manager.registerTest(FailedParserSyntax61())
        manager.registerTest(FailedParserSyntax62())
        manager.registerTest(FailedParserSyntax63())
        manager.registerTest(FailedParserSyntax64())
        manager.registerTest(FailedParserSyntax65())
        manager.registerTest(FailedParserSyntax66())
        manager.registerTest(FailedParserSyntax67())
        manager.registerTest(FailedParserSyntax68())
        manager.registerTest(FailedParserSyntax69())
        manager.registerTest(FailedParserSyntax70())
        manager.registerTest(FailedParserSyntax71())
        manager.registerTest(FailedParserSyntax72())
        manager.registerTest(FailedParserSyntax73())
        manager.registerTest(FailedParserSyntax74())
        manager.registerTest(FailedParserSyntax75())
        manager.registerTest(FailedParserSyntax76())
        manager.registerTest(FailedParserSyntax77())
        manager.registerTest(FailedParserSyntax78())
    }

    private fun config2(manager: SystemTestManager) {
        manager.registerTest(FailedParser1())
        manager.registerTest(de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.FailedParser2())
        manager.registerTest(de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.FailedParser3())
        manager.registerTest(de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.FailedParser4())
        manager.registerTest(de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.FailedParser5())
        manager.registerTest(de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.FailedParser6())
        manager.registerTest(de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.FailedParser7())
        manager.registerTest(de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.FailedParser8())
        manager.registerTest(de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.FailedParser9())
        manager.registerTest(de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.FailedParser10())
        manager.registerTest(de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.FailedParser11())
        manager.registerTest(de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.FailedParser12())
        manager.registerTest(de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.FailedParser13())
        manager.registerTest(de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.FailedParser14())
        manager.registerTest(de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.FailedParser15())
        manager.registerTest(de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.FailedParser16())
        manager.registerTest(de.unisaarland.cs.se.selab.systemtest.failedconfig2parser.FailedParser17())
        manager.registerTest(FailedParser18())
        manager.registerTest(FailedParser19())
        manager.registerTest(FailedParser20())
        manager.registerTest(FailedParser21())
        manager.registerTest(FailedParser22())
        manager.registerTest(FailedParser23())
        manager.registerTest(FailedParser24())
        manager.registerTest(FailedParser25())
        manager.registerTest(FailedParser26())
        manager.registerTest(FailedParser27())
        manager.registerTest(FailedParser28())
        manager.registerTest(FailedParser29())
        manager.registerTest(FailedParser30())
        manager.registerTest(FailedParser31())
        manager.registerTest(FailedParser32())
        manager.registerTest(FailedParser33())
        manager.registerTest(FailedParser34())
        manager.registerTest(FailedParser35())
        manager.registerTest(FailedParser36())
        manager.registerTest(FailedParser37())
        manager.registerTest(FailedParser38())
        manager.registerTest(FailedParser39())
        manager.registerTest(FailedParser40())
        manager.registerTest(FailedParser41())
        manager.registerTest(FailedParser42())
        manager.registerTest(FailedParser43())
        manager.registerTest(FailedParser44())
        manager.registerTest(FailedParser45())
        manager.registerTest(FailedParser46())
        manager.registerTest(FailedParser47())
        manager.registerTest(FailedParser48())
        manager.registerTest(FailedParser49())
        manager.registerTest(FailedParser50())
    }
}

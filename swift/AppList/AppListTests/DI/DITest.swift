import XCTest
@testable import AppList

final class DITest: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testResolver() throws {
        Resolver.shared.add(TestDI.self, component: TestDIType())
        let typeString = String(describing: Resolver.shared.resolve(TestDI.self))
        XCTAssertEqual(typeString, "TestDIType()")
    }
    
    func testInjection() throws {
        Resolver.shared.add(TestDI.self, component: TestDIType())
        @Injection var testDi: TestDI
        let typeString = String(describing: testDi.self)
        XCTAssertEqual(typeString, "TestDIType()")
    }
}

protocol TestDI {}

struct TestDIType: TestDI {}

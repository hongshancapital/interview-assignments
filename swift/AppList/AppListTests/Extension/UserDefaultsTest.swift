import XCTest
@testable import AppList

final class UserDefaultsTest: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testSetValue() throws {
        UserDefaults.Test.test.set(["TestData"])
        XCTAssertEqual(UserDefaults.Test.test.stringArrayValue?.count, 1)
    }

}

extension UserDefaults {
    enum Test: String, UniqueKey {
        case test
    }
}

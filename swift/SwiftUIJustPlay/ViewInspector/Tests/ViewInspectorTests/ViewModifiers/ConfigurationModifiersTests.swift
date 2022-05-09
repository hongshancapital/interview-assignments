import XCTest
import SwiftUI
@testable import ViewInspector

// MARK: - ViewControlAttributesTests

@available(iOS 13.0, macOS 10.15, tvOS 13.0, *)
final class ViewControlAttributesTests: XCTestCase {
    
    func testLabelsHidden() throws {
        let sut = EmptyView().labelsHidden()
        XCTAssertNoThrow(try sut.inspect().emptyView())
    }
    
    func testLabelsHiddenInspection() throws {
        let sut = EmptyView().labelsHidden()
        XCTAssertTrue(try sut.inspect().emptyView().labelsHidden())
    }
    
    #if os(macOS)
    func testHorizontalRadioGroupLayout() throws {
        let sut = EmptyView().horizontalRadioGroupLayout()
        XCTAssertNoThrow(try sut.inspect().emptyView())
    }
    
    func testHorizontalRadioGroupLayoutInspection() throws {
        let sut = EmptyView().horizontalRadioGroupLayout()
        XCTAssertTrue(try sut.inspect().horizontalRadioGroupLayout())
    }
    
    func testControlSize() throws {
        let sut = EmptyView().controlSize(.mini)
        XCTAssertNoThrow(try sut.inspect().emptyView())
    }
    
    func testControlSizeInspection() throws {
        let sut = EmptyView().controlSize(.mini)
        XCTAssertEqual(try sut.inspect().emptyView().controlSize(), .mini)
    }
    #endif
}

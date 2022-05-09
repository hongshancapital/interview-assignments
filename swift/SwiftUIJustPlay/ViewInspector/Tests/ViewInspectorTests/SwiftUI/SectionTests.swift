import XCTest
import SwiftUI
@testable import ViewInspector

@available(iOS 13.0, macOS 10.15, tvOS 13.0, *)
final class SectionTests: XCTestCase {
    
    func testSingleEnclosedView() throws {
        let sampleView = Text("Test")
        let view = Section { sampleView }
        let sut = try view.inspect().section().text(0).content.view as? Text
        XCTAssertEqual(sut, sampleView)
    }
    
    func testSingleEnclosedViewIndexOutOfBounds() throws {
        let sampleView = Text("Test")
        let view = Section { sampleView }
        XCTAssertThrows(
            try view.inspect().section().text(1),
            "Enclosed view index '1' is out of bounds: '0 ..< 1'")
    }
    
    func testMultipleEnclosedViews() throws {
        let sampleView1 = Text("Test")
        let sampleView2 = Text("Abc")
        let sampleView3 = Text("XYZ")
        let view = Section { sampleView1; sampleView2; sampleView3 }
        let view1 = try view.inspect().section().text(0).content.view as? Text
        let view2 = try view.inspect().section().text(1).content.view as? Text
        let view3 = try view.inspect().section().text(2).content.view as? Text
        XCTAssertEqual(view1, sampleView1)
        XCTAssertEqual(view2, sampleView2)
        XCTAssertEqual(view3, sampleView3)
    }
    
    func testMultipleEnclosedViewsIndexOutOfBounds() throws {
        let sampleView1 = Text("Test")
        let sampleView2 = Text("Abc")
        let view = Section { sampleView1; sampleView2 }
        XCTAssertThrows(
            try view.inspect().section().text(2),
            "Enclosed view index '2' is out of bounds: '0 ..< 2'")
    }
    
    func testSearch() throws {
        let view = AnyView(Section(header: Text("h"), footer: Text("f"), content: { Text("c") }))
        XCTAssertEqual(try view.inspect().find(ViewType.Section.self).pathToRoot,
                       "anyView().section()")
        XCTAssertEqual(try view.inspect().find(text: "c").pathToRoot,
                       "anyView().section().text(0)")
        XCTAssertEqual(try view.inspect().find(text: "h").pathToRoot,
                       "anyView().section().header().text()")
        XCTAssertEqual(try view.inspect().find(text: "f").pathToRoot,
                       "anyView().section().footer().text()")
    }
    
    func testResetsModifiers() throws {
        let view = Section { Text("Test") }.padding()
        let sut = try view.inspect().section().text(0)
        XCTAssertEqual(sut.content.medium.viewModifiers.count, 0)
    }
    
    func testExtractionFromSingleViewContainer() throws {
        let view = AnyView(Section { Text("Test") })
        XCTAssertNoThrow(try view.inspect().anyView().section())
    }
    
    func testExtractionFromMultipleViewContainer() throws {
        let view = Form {
            Section { Text("Test") }
            Section { Text("Test") }
        }
        XCTAssertNoThrow(try view.inspect().form().section(0))
        XCTAssertNoThrow(try view.inspect().form().section(1))
    }
    
    func testHeaderInspection() throws {
        let view = Section(header: Text("header"), footer: Text("footer"), content: { Text("content") })
        let sut = try view.inspect().section().header().text(0).string()
        XCTAssertEqual(sut, "header")
    }
    
    func testFooterInspection() throws {
        let view = Section(header: Text("header"), footer: Text("footer"), content: { Text("content") })
        let sut = try view.inspect().section().footer().text(0).string()
        XCTAssertEqual(sut, "footer")
    }
}

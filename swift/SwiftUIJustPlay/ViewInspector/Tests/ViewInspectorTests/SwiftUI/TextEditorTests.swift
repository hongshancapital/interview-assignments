import XCTest
import SwiftUI
@testable import ViewInspector

#if os(iOS) || os(macOS)
@available(iOS 13.0, macOS 10.15, *)
@available(tvOS, unavailable)
@available(watchOS, unavailable)
final class TextEditorTests: XCTestCase {
    
    func testExtractionFromSingleViewContainer() throws {
        guard #available(iOS 14, tvOS 14, macOS 11.0, watchOS 7.0, *)
        else { throw XCTSkip() }
        let binding = Binding(wrappedValue: "")
        let view = AnyView(TextEditor(text: binding))
        XCTAssertNoThrow(try view.inspect().anyView().textEditor())
    }
    
    func testExtractionFromMultipleViewContainer() throws {
        guard #available(iOS 14, tvOS 14, macOS 11.0, watchOS 7.0, *)
        else { throw XCTSkip() }
        let binding = Binding(wrappedValue: "")
        let view = HStack {
            Text("Test")
            TextEditor(text: binding)
        }
        XCTAssertNoThrow(try view.inspect().hStack().textEditor(1))
    }
    
    func testSearch() throws {
        guard #available(iOS 14, tvOS 14, macOS 11.0, watchOS 7.0, *)
        else { throw XCTSkip() }
        let binding = Binding(wrappedValue: "")
        let view = AnyView(TextEditor(text: binding))
        XCTAssertEqual(try view.inspect().find(ViewType.TextEditor.self).pathToRoot,
                       "anyView().textEditor()")
    }
    
    func testInput() throws {
        guard #available(iOS 14, tvOS 14, macOS 11.0, watchOS 7.0, *)
        else { throw XCTSkip() }
        let binding = Binding(wrappedValue: "123")
        let view = TextEditor(text: binding)
        let sut = try view.inspect().textEditor()
        XCTAssertEqual(try sut.input(), "123")
        try sut.setInput("abc")
        XCTAssertEqual(try sut.input(), "abc")
    }
    
    func testSetInputWhenDisabled() throws {
        guard #available(iOS 14, tvOS 14, macOS 11.0, watchOS 7.0, *)
        else { throw XCTSkip() }
        let binding = Binding(wrappedValue: "123")
        let view = TextEditor(text: binding).disabled(true)
        let sut = try view.inspect().textEditor()
        XCTAssertThrows(try sut.setInput("abc"),
            "TextEditor is unresponsive: it is disabled")
        XCTAssertEqual(try sut.input(), "123")
    }
}
#endif

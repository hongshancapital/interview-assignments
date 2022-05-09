import XCTest
import SwiftUI
@testable import ViewInspector

@available(iOS 13.0, macOS 10.15, tvOS 13.0, *)
final class DeprecatedAlertTests: XCTestCase {
    
    func testInspectionNotBlocked() throws {
        let binding = Binding(wrappedValue: true)
        let sut = EmptyView().alert(isPresented: binding) { Alert(title: Text("abc")) }
        XCTAssertNoThrow(try sut.inspect().emptyView())
    }
    
    func testInspectionErrorNoModifier() throws {
        let sut = EmptyView().offset()
        XCTAssertThrows(try sut.inspect().emptyView().alert(),
                        "EmptyView does not have 'alert' modifier")
    }
    
    func testInspectionErrorCustomModifierRequired() throws {
        let binding = Binding(wrappedValue: true)
        let sut = EmptyView().alert(isPresented: binding) { Alert(title: Text("abc")) }
        XCTAssertThrows(try sut.inspect().emptyView().alert(),
            """
            Please refer to the Guide for inspecting the Alert: \
            https://github.com/nalexn/ViewInspector/blob/master/guide_popups.md#alert
            """)
    }
    
    func testInspectionErrorAlertNotPresented() throws {
        let binding = Binding(wrappedValue: false)
        let sut = EmptyView().alert2(isPresented: binding) { Alert(title: Text("abc")) }
        XCTAssertThrows(try sut.inspect().emptyView().alert(),
                        "View for Alert is absent")
        XCTAssertThrows(try sut.inspect().find(text: "abc"), "Search did not find a match")
    }
    
    func testInspectionErrorAlertWithItemNotPresented() throws {
        let binding = Binding<Int?>(wrappedValue: nil)
        let sut = EmptyView().alert2(item: binding) { value in
            Alert(title: Text("\(value)"))
        }
        XCTAssertThrows(try sut.inspect().emptyView().alert(),
                        "View for Alert is absent")
    }
    
    func testTitleInspection() throws {
        let binding = Binding(wrappedValue: true)
        let sut = EmptyView().alert2(isPresented: binding) { Alert(title: Text("abc")) }
        let title = try sut.inspect().emptyView().alert().title()
        XCTAssertEqual(try title.string(), "abc")
        XCTAssertEqual(title.pathToRoot, "emptyView().alert().title()")
    }
    
    func testMessageInspection() throws {
        let binding = Binding(wrappedValue: true)
        let sut = EmptyView().alert2(isPresented: binding) {
            Alert(title: Text("abc"), message: Text("123"), dismissButton: nil)
        }
        let message = try sut.inspect().emptyView().alert().message()
        XCTAssertEqual(try message.text().string(), "123")
        XCTAssertEqual(message.pathToRoot, "emptyView().alert().message()")
    }
    
    func testNoMessageError() throws {
        let binding = Binding(wrappedValue: true)
        let sut = EmptyView().alert2(isPresented: binding) { Alert(title: Text("abc")) }
        XCTAssertThrows(try sut.inspect().emptyView().alert().message(),
                        "View for message is absent")
    }
    
    func testPrimaryButtonInspection() throws {
        let binding = Binding(wrappedValue: true)
        let sut = EmptyView().alert2(isPresented: binding) {
            Alert(title: Text("abc"), message: Text("123"),
                  dismissButton: .default(Text("xyz")))
        }
        let label = try sut.inspect().emptyView().alert().primaryButton().labelView()
        XCTAssertEqual(try label.string(), "xyz")
        XCTAssertEqual(label.pathToRoot, "emptyView().alert().primaryButton().labelView()")
    }
    
    func testSecondaryButtonInspection() throws {
        let binding = Binding(wrappedValue: true)
        let sut = EmptyView().alert2(isPresented: binding) {
            Alert(title: Text(""), primaryButton: .cancel(),
                  secondaryButton: .default(Text("xyz")))
        }
        let label = try sut.inspect().emptyView().alert().secondaryButton().labelView()
        XCTAssertEqual(try label.string(), "xyz")
        XCTAssertEqual(label.pathToRoot, "emptyView().alert().secondaryButton().labelView()")
    }
    
    func testNoSecondaryButtonError() throws {
        let binding = Binding(wrappedValue: true)
        let sut = EmptyView().alert2(isPresented: binding) { Alert(title: Text("abc")) }
        XCTAssertThrows(try sut.inspect().emptyView().alert().secondaryButton(),
                        "View for secondaryButton is absent")
    }
    
    func testTapOnPrimaryButtonWithoutCallback() throws {
        let binding = Binding(wrappedValue: true)
        let sut = EmptyView().alert2(isPresented: binding) {
            Alert(title: Text("abc"), message: Text("123"),
                  dismissButton: .default(Text("xyz")))
        }
        XCTAssertTrue(binding.wrappedValue)
        try sut.inspect().emptyView().alert().primaryButton().tap()
        XCTAssertFalse(binding.wrappedValue)
    }
    
    func testTapOnPrimaryButtonWithCallback() throws {
        let exp = XCTestExpectation(description: #function)
        let binding = Binding(wrappedValue: true)
        let sut = EmptyView().alert2(isPresented: binding) {
            Alert(title: Text("abc"), message: Text("123"),
                  dismissButton: .destructive(Text("xyz"), action: {
                    exp.fulfill()
                  }))
        }
        XCTAssertTrue(binding.wrappedValue)
        try sut.inspect().emptyView().alert().primaryButton().tap()
        XCTAssertFalse(binding.wrappedValue)
        wait(for: [exp], timeout: 0.1)
    }
    
    func testTapOnSecondaryButtonWithoutCallback() throws {
        let binding = Binding(wrappedValue: true)
        let sut = EmptyView().alert2(isPresented: binding) {
            Alert(title: Text("abc"), primaryButton: .default(Text("xyz")),
                  secondaryButton: .default(Text("123")))
        }
        XCTAssertTrue(binding.wrappedValue)
        try sut.inspect().emptyView().alert().secondaryButton().tap()
        XCTAssertFalse(binding.wrappedValue)
    }
    
    func testTapOnSecondaryButtonWithCallback() throws {
        let exp = XCTestExpectation(description: #function)
        let binding = Binding(wrappedValue: true)
        let sut = EmptyView().alert2(isPresented: binding) {
            Alert(title: Text("abc"), primaryButton: .default(Text("xyz")),
                  secondaryButton: .default(Text("123"), action: {
                    exp.fulfill()
                  }))
        }
        XCTAssertTrue(binding.wrappedValue)
        try sut.inspect().emptyView().alert().secondaryButton().tap()
        XCTAssertFalse(binding.wrappedValue)
        wait(for: [exp], timeout: 0.1)
    }
    
    func testAlertButtonStyle() throws {
        let binding = Binding(wrappedValue: true)
        let sut1 = EmptyView().alert2(isPresented: binding) {
            Alert(title: Text(""), primaryButton: .default(Text("")),
                  secondaryButton: .cancel(Text("")))
        }
        let sut2 = EmptyView().alert2(isPresented: binding) {
            Alert(title: Text(""), message: nil, dismissButton: .destructive(Text("")))
        }
        XCTAssertEqual(
            try sut1.inspect().emptyView().alert().primaryButton().style(), .default)
        XCTAssertEqual(
            try sut1.inspect().emptyView().alert().secondaryButton().style(), .cancel)
        XCTAssertEqual(
            try sut2.inspect().emptyView().alert().primaryButton().style(), .destructive)
    }
    
    func testAlertWithItem() throws {
        let binding = Binding<Int?>(wrappedValue: 6)
        let sut = EmptyView().alert2(item: binding) { value in
            Alert(title: Text("\(value)"))
        }
        XCTAssertEqual(try sut.inspect().emptyView().alert().title().string(), "6")
        XCTAssertEqual(binding.wrappedValue, 6)
        try sut.inspect().emptyView().alert().primaryButton().tap()
        XCTAssertNil(binding.wrappedValue)
    }
    
    func testDismiss() throws {
        let binding = Binding(wrappedValue: true)
        let sut = EmptyView().alert2(isPresented: binding) {
            Alert(title: Text("abc"))
        }
        XCTAssertTrue(binding.wrappedValue)
        try sut.inspect().alert().dismiss()
        XCTAssertFalse(binding.wrappedValue)
        XCTAssertThrows(try sut.inspect().alert(), "View for Alert is absent")
    }
    
    func testDismissForItemVersion() throws {
        let binding = Binding<Int?>(wrappedValue: 6)
        let sut = EmptyView().alert2(item: binding) { value in
            Alert(title: Text("\(value)"))
        }
        try sut.inspect().emptyView().alert().dismiss()
        XCTAssertNil(binding.wrappedValue)
        XCTAssertThrows(try sut.inspect().alert(), "View for Alert is absent")
    }
    
    func testMultipleAlertsInspection() throws {
        let binding1 = Binding(wrappedValue: true)
        let binding2 = Binding(wrappedValue: true)
        let binding3 = Binding(wrappedValue: true)
        let sut = AlertFindTestView(alert1: binding1, alert2: binding2, alert3: binding3)
        let title1 = try sut.inspect().hStack().emptyView(0).alert().title()
        XCTAssertEqual(try title1.string(), "title_1")
        XCTAssertEqual(title1.pathToRoot,
            "view(AlertFindTestView.self).hStack().emptyView(0).alert().title()")
        let title2 = try sut.inspect().hStack().emptyView(0).alert(1).title()
        XCTAssertEqual(try title2.string(), "title_3")
        XCTAssertEqual(title2.pathToRoot,
            "view(AlertFindTestView.self).hStack().emptyView(0).alert(1).title()")
        
        XCTAssertEqual(try sut.inspect().find(ViewType.Alert.self).title().string(), "title_1")
        binding1.wrappedValue = false
        XCTAssertEqual(try sut.inspect().find(ViewType.Alert.self).title().string(), "title_3")
        binding3.wrappedValue = false
        XCTAssertThrows(try sut.inspect().find(ViewType.Alert.self), "Search did not find a match")
    }
    
    func testFindAndPathToRoots() throws {
        let binding = Binding(wrappedValue: true)
        let sut = AlertFindTestView(alert1: binding, alert2: binding, alert3: binding)
        
        // 1
        XCTAssertEqual(try sut.inspect().find(text: "title_1").pathToRoot,
            "view(AlertFindTestView.self).hStack().emptyView(0).alert().title()")
        XCTAssertEqual(try sut.inspect().find(text: "message_1").pathToRoot,
            "view(AlertFindTestView.self).hStack().emptyView(0).alert().message().text()")
        XCTAssertEqual(try sut.inspect().find(text: "primary_1").pathToRoot,
            "view(AlertFindTestView.self).hStack().emptyView(0).alert().primaryButton().labelView()")
        XCTAssertEqual(try sut.inspect().find(text: "secondary_1").pathToRoot,
            "view(AlertFindTestView.self).hStack().emptyView(0).alert().secondaryButton().labelView()")
        // 2
        let noMatchMessage: String
        if #available(iOS 13.2, tvOS 13.2, macOS 10.17, *) {
            noMatchMessage = "Search did not find a match"
        } else {
            noMatchMessage = "Search did not find a match. Possible blockers: Alert, Alert"
        }
        XCTAssertThrows(try sut.inspect().find(text: "title_2").pathToRoot, noMatchMessage)
        
        // 3
        XCTAssertEqual(try sut.inspect().find(text: "title_3").pathToRoot,
            "view(AlertFindTestView.self).hStack().emptyView(0).alert(1).title()")
        XCTAssertThrows(try sut.inspect().find(text: "message_3").pathToRoot, noMatchMessage)
        XCTAssertEqual(try sut.inspect().find(text: "primary_3").pathToRoot,
            "view(AlertFindTestView.self).hStack().emptyView(0).alert(1).primaryButton().labelView()")
    }
}
 
@available(iOS 13.0, macOS 10.15, tvOS 13.0, *)
final class AlertIOS15Tests: XCTestCase {
    
    @available(iOS 15.0, macOS 12.0, tvOS 15.0, watchOS 8.0, *)
    private func sutIOS15(binding: Binding<Bool>) -> some View {
        let param: String? = "abc"
        return EmptyView().alert("Title", isPresented: binding, presenting: param,
                                    actions: { param in
            Button(role: .destructive) { } label: { Text(param) }
            Button("Second") { }
        }, message: { param in
            HStack { Text("Message: \(param)") }
        })
    }
    
    func testAlertInspectioniOS15() throws {
        guard #available(iOS 15.0, macOS 12.0, tvOS 15.0, watchOS 8.0, *)
        else { throw XCTSkip() }
        let binding = Binding(wrappedValue: true)
        let sut = sutIOS15(binding: binding)
        let alert = try sut.inspect().alert()
        XCTAssertEqual(try alert.title().string(), "Title")
        let message = try alert.message().hStack().text(0)
        XCTAssertEqual(try message.string(), "Message: abc")
        XCTAssertEqual(message.pathToRoot,
                       "alert().message().hStack().text(0)")
        let secondButtonLabel = try alert.actions().button(1).labelView().text()
        XCTAssertEqual(try secondButtonLabel.string(), "Second")
        XCTAssertEqual(secondButtonLabel.pathToRoot,
                       "alert().actions().button(1).labelView().text()")
        let searchLabel = try sut.inspect().find(button: "Second")
        XCTAssertEqual(searchLabel.pathToRoot,
                       "emptyView().alert().actions().button(1)")
    }
}

extension Int: Identifiable {
    public var id: Int { self }
}

extension String: Identifiable {
    public var id: String { self }
}

@available(iOS 13.0, macOS 10.15, tvOS 13.0, *)
extension View {
    func alert2(isPresented: Binding<Bool>,
                content: @escaping () -> Alert) -> some View {
        return self.modifier(InspectableAlert(isPresented: isPresented, popupBuilder: content))
    }
    
    func alert2<Item>(item: Binding<Item?>,
                      content: @escaping (Item) -> Alert
    ) -> some View where Item: Identifiable {
        return self.modifier(InspectableAlertWithItem(item: item, popupBuilder: content))
    }
}

@available(iOS 13.0, macOS 10.15, tvOS 13.0, *)
private struct InspectableAlert: ViewModifier, PopupPresenter {
    
    let isPresented: Binding<Bool>
    let popupBuilder: () -> Alert
    let onDismiss: (() -> Void)? = nil
    
    func body(content: Self.Content) -> some View {
        content.alert(isPresented: isPresented, content: popupBuilder)
    }
}

@available(iOS 13.0, macOS 10.15, tvOS 13.0, *)
private struct InspectableAlertWithItem<Item: Identifiable>: ViewModifier, ItemPopupPresenter {
    let item: Binding<Item?>
    let popupBuilder: (Item) -> Alert
    let onDismiss: (() -> Void)? = nil
    
    func body(content: Self.Content) -> some View {
        content.alert(item: item, content: popupBuilder)
    }
}

@available(iOS 13.0, macOS 10.15, tvOS 13.0, *)
private struct AlertFindTestView: View, Inspectable {
    
    @Binding var isAlert1Presented = false
    @Binding var isAlert2Presented = false
    @Binding var isAlert3Presented = false
    
    init(alert1: Binding<Bool>, alert2: Binding<Bool>, alert3: Binding<Bool>) {
        _isAlert1Presented = alert1
        _isAlert2Presented = alert2
        _isAlert3Presented = alert3
    }
    
    var body: some View {
        HStack {
            EmptyView()
                .alert2(isPresented: $isAlert1Presented) {
                    Alert(title: Text("title_1"),
                          message: Text("message_1"),
                          primaryButton: .default(Text("primary_1")),
                          secondaryButton: .destructive(Text("secondary_1")))
                }
                .alert(isPresented: $isAlert2Presented) {
                    Alert(title: Text("title_2"))
                }
                .alert2(isPresented: $isAlert3Presented) {
                    Alert(title: Text("title_3"), message: nil,
                          dismissButton: .cancel(Text("primary_3")))
                }
        }
    }
}

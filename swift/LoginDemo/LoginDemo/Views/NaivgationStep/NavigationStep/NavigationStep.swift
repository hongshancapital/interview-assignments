//
//  NavigationStep.swift
//  
//
//  Created by Alex Nagy on 11.03.2021.
//

import SwiftUI

/// A view that controls a navigation presentation with a unified and powerfull syntax
///
/// `NavigationStep` is a fully fledged replacement for, and buit on top of `NavigationLink`, `.sheet` and `.fullScreenCover`. It simplifies and unifies the navigation syntax into a consistent one. It adds extra functionality like `onDismiss` and `action` completion handlers.
///
/// `NavigationStep` works perfectly alongside `SwiftUI`'s built in navigation system. It's not trying to remove the existing `SwiftUI` navigation system, rather acting as a unified and more powerfull syntax built on top of it.
///
/// `NavigationStep` comes in 3 flavors:
///
/// 1. `View` that when tapped presents a `destination` view.
/// 2. `EmptyView` with `isActive` `Binding<Bool>` that presents a `destination` view when `isActive` is set to `true`.
/// 3. `View` that when tapped executes an `action` that can present a `destination` view when `isActive` is set to `true`.
///
/// You may also use a `NavigationStep` that presents the `destination` view when
/// a bound selection variable equals a given tag value.
///
/// 1. `View` that when tapped presents a `destination` view when `selection` is set to `tag`.
/// 2. `EmptyView` that presents a `destination` view when `selection` is set to `tag`.
/// 3. `View` that when tapped executes an `action` that can present a `destination` view when `selection` is set to `tag`.
///
/// Navigation in `SwiftUI` is handeled by multiple items (ex. NavigationLink, .sheet, .fullScreenCover). `NavigationStep` brings them all into one convenient syntax.
/// `NavigationStep` behaves much like a `NavigationLink`, but with added extras.
///
/// The three use cases of `NavigationStep` are:
/// 1. when you want to navigate to another `NavigationStep` upon a user initiated tap on a view
/// 2. when you want to navigate to another `NavigationStep` but no tappable view should be available on the screen
/// 3. when you want to navigate to another `NavigationStep` upon a user initiated tap on a view, but only after a certain action has been finished after the tap
///
///
/// IMPORTANT: Your root view has to be inside a `NavigationView`:
///
/// ```
/// NavigationView {
///     ContentView()
/// }
/// ```
///
/// Let's take a look at some examples:
///
/// Inside `ContentView` you can create a `NavigationStep` that will navigate to the `DetailView`:
///
/// ```
/// NavigationStep(type: .push, style: .button) {
///     DetailView()
/// } label: {
///     Text("Go to DetailView")
/// }
/// ```
///
/// The same outcome can be achieved with the other initializer:
///
/// ```
/// @State private var isDetailViewActive = false // declared outside of the body
/// ```
///
/// ```
/// Button {
///     isDetailViewActive.toggle()
/// } label: {
///     Text("Go to DetailView")
/// }
/// NavigationStep(type: .push, isActive: $isDetailViewActive) {
///     DetailView()
/// }
/// ```
///
/// You can also choose the label style. Options are: `.button` and `.view`.
///
/// ```
/// NavigationStep(type: .push, style: .view) {
///     DetailView()
/// } label: {
///     Text("Go to DetailView")
/// }
/// ```
///
/// There's an option to listen to `onDismiss` events trigerred when the `NavigationStep` is dismissed:
///
/// ```
/// NavigationStep(type: .push, style: .view) {
///     DetailView()
/// } label: {
///     Text("Go to DetailView")
/// } onDismiss: {
///     print("DetailView was dismissed")
/// }
/// ```
///
/// Also you may choose the type of navigation. Options are: `.push`, `.sheet` and `.fullScreenSheet`.
///
/// ```
/// NavigationStep(type: .sheet, style: .view) {
///     DetailView()
/// } label: {
///     Text("Go to DetailView")
/// }
/// ```
///
/// If you wish you may add an `action` between the moment the view is tapped and the presentation of the `destination`. Optionally you may add an `onDismiss` completion here too.
///
/// ```
/// @State private var isDetailViewActive = false // declared outside of the body
/// ```
///
/// ```
/// NavigationStep(type: .sheet, style: .button, isActive: $isDetailViewActive) {
///     DetailView()
/// } label: {
///     Text("Go to DetailView")
/// } action: {
///     // do some work here
///     DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
///         // work has finished; set `isActive` to `true`
///         isDetailViewActive.toggle()
///     }
/// } onDismiss: {
///     print("Dismissed DetailView")
/// }
/// ```
///
/// You may also use a `NavigationStep` that presents the `destination` view when
/// a bound selection variable equals a given tag value.
///
/// ```
/// @State private var selection: Int? = nil
/// ```
///
/// ```
/// ForEach(0...9, id:\.self) { index in
///     NavigationStep(type: .push, style: .button, tag: index, selection: $selection) {
///         DetailView(selection: $selection, index: index)
///     } label: {
///         Text("Label \(index)")
///     } action: {
///         DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
///             selection = index
///         }
///     }
/// }
/// ```
///
/// You can now dismiss the `DetailView` by setting:
///
/// ```
/// selection = nil
/// ```
///
/// Or jump to another `DetailView`:
///
/// ```
/// selection = 3
/// ```
///
public struct NavigationStep<Destination: View, Label: View>: View {
    
    @State private var isActive = false
    @State private var isDisabled = false
    
    private var navigationStepType: NavigationStepType
    private var navigationStepStyle: NavigationStepStyle?
    @Binding private var isActiveBinding: Bool
    private var tag: Int?
    @Binding private var selection: Int?
    private let destination: Destination
    private let label: Label
    private let action: (() -> Void)?
    private let onDismiss: (() -> Void)?
    
    /// `View` that when tapped presents a `destination` view.
    /// - Parameters:
    ///   - type: The NavigationStep type.
    ///   - style: The NavigationStep style.
    ///   - destination: A view builder to produce the view the navigation step to present.
    ///   - label: A view builder to produce a label describing the `destination` to present.
    ///   - onDismiss: A closure executed when the navigation dismisses the active/presented view. If `onDismiss` is `nil`, the call has no effect.
    public init(type: NavigationStepType,
                style: NavigationStepStyle,
                @ViewBuilder destination: () -> Destination,
                @ViewBuilder label: () -> Label,
                onDismiss: (() -> Void)? = nil) {
        self.navigationStepType = type
        self.navigationStepStyle = style
        self._isActiveBinding = .constant(false)
        self.tag = nil
        self._selection = .constant(nil)
        self.destination = destination()
        self.label = label()
        self.action = nil
        self.onDismiss = onDismiss
    }
    
    public var body: some View {
        VStack {
            switch navigationStepType {
            case .push:
                push(style: navigationStepStyle)
            case .sheet:
                sheet(isFullScreen: false, style: navigationStepStyle)
            case .fullScreenSheet:
                sheet(isFullScreen: true, style: navigationStepStyle)
            }
        }
    }
    
}

public extension NavigationStep where Label == EmptyView {
    
    /// `EmptyView` with `isActive` `Binding<Bool>` that presents a `destination` view when `isActive` is set to `true`.
    /// - Parameters:
    ///   - type: The NavigationStep type.
    ///   - isActive: A binding to a Boolean value that indicates whether the `destination` is currently presented.
    ///   - destination: A view builder to produce the view the navigation step to present.
    ///   - onDismiss: A closure executed when the navigation dismisses the active/presented view. If `onDismiss` is `nil`, the call has no effect.
    init(type: NavigationStepType,
         isActive: Binding<Bool>,
         @ViewBuilder destination: () -> Destination,
         onDismiss: (() -> Void)? = nil) {
        self.navigationStepType = type
        self.navigationStepStyle = nil
        self._isActiveBinding = isActive
        self.tag = nil
        self._selection = .constant(nil)
        self.destination = destination()
        self.label = { EmptyView() }()
        self.action = nil
        self.onDismiss = onDismiss
    }
}

public extension NavigationStep {
    
    /// `View` that when tapped executes an `action` that can present a `destination` view when `isActive` is set to `true`.
    /// - Parameters:
    ///   - type: The NavigationStep type.
    ///   - style: The NavigationStep style.
    ///   - isActive: A binding to a Boolean value that indicates whether the `destination` is currently presented.
    ///   - destination: A view builder to produce the view the navigation step to present.
    ///   - label: A view builder to produce a label describing the `destination` to present.
    ///   - action: A closure executed when the `label` is tapped.
    ///   - onDismiss: A closure executed when the navigation dismisses the active/presented view. If `onDismiss` is `nil`, the call has no effect.
    init(type: NavigationStepType,
         style: NavigationStepStyle,
         isActive: Binding<Bool>,
         @ViewBuilder destination: () -> Destination,
         @ViewBuilder label: () -> Label,
         action: (() -> Void)?,
         onDismiss: (() -> Void)? = nil) {
        self.navigationStepType = type
        self.navigationStepStyle = style
        self._isActiveBinding = isActive
        self.tag = nil
        self._selection = .constant(nil)
        self.destination = destination()
        self.label = label()
        self.action = action
        self.onDismiss = onDismiss
    }
}

public extension NavigationStep where Label == EmptyView {
    
    /// `EmptyView` that presents a `destination` view when `selection` is set to `tag`.
    /// - Parameters:
    ///   - type: The NavigationStep type.
    ///   - tag: The value of `selection` that causes the link to present `destination`.
    ///   - selection: A bound variable that causes the link to present `destination` when `selection` becomes equal to `tag`.
    ///   - destination: A view builder to produce the view the navigation step to present.
    ///   - onDismiss: A closure executed when the navigation dismisses the active/presented view. If `onDismiss` is `nil`, the call has no effect.
    init(type: NavigationStepType,
         tag: Int?,
         selection: Binding<Int?>,
         @ViewBuilder destination: () -> Destination,
         onDismiss: (() -> Void)? = nil) {
        self.navigationStepType = type
        self.navigationStepStyle = nil
        self._isActiveBinding = .constant(false)
        self.tag = tag
        self._selection = selection
        self.destination = destination()
        self.label = { EmptyView() }()
        self.action = nil
        self.onDismiss = onDismiss
    }
}

public extension NavigationStep {
    
    /// `View` that when tapped presents a `destination` view when `selection` is set to `tag`.
    /// - Parameters:
    ///   - type: The NavigationStep type.
    ///   - style: The NavigationStep style.
    ///   - tag: The value of `selection` that causes the link to present `destination`.
    ///   - selection: A bound variable that causes the link to present `destination` when `selection` becomes equal to `tag`.
    ///   - destination: A view builder to produce the view the navigation step to present.
    ///   - label: A view builder to produce a label that triggers the `action` to be executed.
    ///   - onDismiss: A closure executed when the navigation dismisses the active/presented view. If `onDismiss` is `nil`, the call has no effect.
    init(type: NavigationStepType,
         style: NavigationStepStyle,
         tag: Int?,
         selection: Binding<Int?>,
         @ViewBuilder destination: () -> Destination,
         @ViewBuilder label: () -> Label,
         onDismiss: (() -> Void)? = nil) {
        self.navigationStepType = type
        self.navigationStepStyle = style
        self._isActiveBinding = .constant(false)
        self.tag = tag
        self._selection = selection
        self.destination = destination()
        self.label = label()
        self.action = nil
        self.onDismiss = onDismiss
    }
}

public extension NavigationStep {
    
    /// `View` that when tapped executes an `action` that can present a `destination` view when `selection` is set to `tag`.
    /// - Parameters:
    ///   - type: The NavigationStep type.
    ///   - style: The NavigationStep style.
    ///   - tag: The value of `selection` that causes the link to present `destination`.
    ///   - selection: A bound variable that causes the link to present `destination` when `selection` becomes equal to `tag`.
    ///   - destination: A view builder to produce the view the navigation step to present.
    ///   - label: A view builder to produce a label that triggers the `action` to be executed.
    ///   - action: A closure executed when the `label` is tapped.
    ///   - onDismiss: A closure executed when the navigation dismisses the active/presented view. If `onDismiss` is `nil`, the call has no effect.
    init(type: NavigationStepType,
         style: NavigationStepStyle,
         tag: Int?,
         selection: Binding<Int?>,
         @ViewBuilder destination: () -> Destination,
         @ViewBuilder label: () -> Label,
         action: (() -> Void)?,
         onDismiss: (() -> Void)? = nil) {
        self.navigationStepStyle = style
        self.navigationStepType = type
        self._isActiveBinding = .constant(false)
        self.tag = tag
        self._selection = selection
        self.destination = destination()
        self.label = label()
        self.action = action
        self.onDismiss = onDismiss
    }
}

public extension NavigationStep {
    
    // MARK: -
    
    @ViewBuilder
    func push(style: NavigationStepStyle?) -> some View {
        if let navigationStepStyle = navigationStepStyle {
            switch navigationStepStyle {
            case .button:
                buttonPush()
            case .view:
                viewPush()
            }
        } else {
            emptyViewPush()
        }
    }
    
    @ViewBuilder
    func sheet(isFullScreen: Bool, style: NavigationStepStyle?) -> some View {
        if isFullScreen {
            if let navigationStepStyle = navigationStepStyle {
                switch navigationStepStyle {
                case .button:
                    buttonSheet(isFullScreen: true)
                case .view:
                    viewSheet(isFullScreen: true)
                }
            } else {
                emptyViewSheet(isFullScreen: true)
            }
        } else {
            if let navigationStepStyle = navigationStepStyle {
                switch navigationStepStyle {
                case .button:
                    buttonSheet(isFullScreen: false)
                case .view:
                    viewSheet(isFullScreen: false)
                }
            } else {
                emptyViewSheet(isFullScreen: false)
            }
        }
    }
    
    // MARK: -
    
    @ViewBuilder
    func buttonPush() -> some View {
        if let action = action {
            if let tag = tag {
                pushButton(action: action, tag: tag)
            } else {
                pushButton(action: action)
            }
        } else {
            if let tag = tag {
                pushButton(tag: tag)
            } else {
                pushButton()
            }
        }
    }
    
    @ViewBuilder
    func viewPush() -> some View {
        if let action = action {
            if let tag = tag {
                pushView(action: action, tag: tag)
            } else {
                pushView(action: action)
            }
        } else {
            if let tag = tag {
                pushView(tag: tag)
            } else {
                pushView()
            }
        }
    }
    
    @ViewBuilder
    func emptyViewPush() -> some View {
        if let tag = tag {
            pushEmptyView(tag: tag)
        } else {
            pushEmptyView()
        }
    }
    
    @ViewBuilder
    func buttonSheet(isFullScreen: Bool) -> some View {
        if let action = action {
            if let tag = tag {
                sheetButton(isFullScreen: isFullScreen, action: action, tag: tag)
            } else {
                sheetButton(isFullScreen: isFullScreen, action: action)
            }
        } else {
            if let tag = tag {
                sheetButton(isFullScreen: isFullScreen, tag: tag)
            } else {
                sheetButton(isFullScreen: isFullScreen)
            }
        }
    }
    
    @ViewBuilder
    func viewSheet(isFullScreen: Bool) -> some View {
        if let action = action {
            if let tag = tag {
                sheetView(isFullScreen: isFullScreen, action: action, tag: tag)
            } else {
                sheetView(isFullScreen: isFullScreen, action: action)
            }
        } else {
            if let tag = tag {
                sheetView(isFullScreen: isFullScreen, tag: tag)
            } else {
                sheetView(isFullScreen: isFullScreen)
            }
        }
    }
    
    @ViewBuilder
    func emptyViewSheet(isFullScreen: Bool) -> some View {
        if let tag = tag {
            sheetEmptyView(isFullScreen: isFullScreen, tag: tag)
        } else {
            sheetEmptyView(isFullScreen: isFullScreen)
        }
    }
    
    // MARK: -
    
    @ViewBuilder
    func pushButton(action: @escaping (() -> Void), tag: Int) -> some View {
        Button(action: {
            action()
        }, label: {
            label
        })
        NavigationLink(destination: destination.onDisappear(perform: onDismiss), tag: tag, selection: $selection) {
            EmptyView()
        }
    }
    
    @ViewBuilder
    func pushButton(action: @escaping (() -> Void)) -> some View {
        Button(action: {
            action()
        }, label: {
            label
        })
        NavigationLink(destination: destination.onDisappear(perform: onDismiss), isActive: $isActiveBinding, label: {
            EmptyView()
        })
    }
    
    @ViewBuilder
    func pushButton(tag: Int) -> some View {
        NavigationLink(destination: destination.onDisappear(perform: onDismiss), tag: tag, selection: $selection) {
            label
        }
    }
    
    @ViewBuilder
    func pushButton() -> some View {
        NavigationLink(destination: destination.onDisappear(perform: onDismiss)) {
            label
        }
    }
    
    // MARK: -
    
    @ViewBuilder
    func pushView(action: @escaping (() -> Void), tag: Int) -> some View {
        label.onTapGesture {
            action()
        }
        NavigationLink(destination: destination.onDisappear(perform: onDismiss), tag: tag, selection: $selection) {
            EmptyView()
        }
    }
    
    @ViewBuilder
    func pushView(action: @escaping (() -> Void)) -> some View {
        label.onTapGesture {
            action()
        }
        NavigationLink(destination: destination.onDisappear(perform: onDismiss), isActive: $isActiveBinding) {
            EmptyView()
        }
    }
    
    @ViewBuilder
    func pushView(tag: Int) -> some View {
        label.onTapGesture {
            selection = tag
        }
        NavigationLink(destination: destination.onDisappear(perform: onDismiss), tag: tag, selection: $selection) {
            EmptyView()
        }
    }
    
    @ViewBuilder
    func pushView() -> some View {
        label.onTapGesture {
            isActive.toggle()
        }
        NavigationLink(destination: destination.onDisappear(perform: onDismiss), isActive: $isActive) {
            EmptyView()
        }
    }
    
    // MARK: -
    
    @ViewBuilder
    func pushEmptyView(tag: Int) -> some View {
        NavigationLink(destination: destination.onDisappear(perform: onDismiss), tag: tag, selection: $selection) {
            EmptyView()
        }
    }
    
    @ViewBuilder
    func pushEmptyView() -> some View {
        NavigationLink(destination: destination.onDisappear(perform: onDismiss), isActive: $isActiveBinding) {
            EmptyView()
        }
    }
    
    // MARK: -
    
    @ViewBuilder
    func sheetButton(isFullScreen: Bool, action: @escaping (() -> Void), tag: Int) -> some View {
        if isFullScreen {
            Button {
                action()
            } label: {
                label
            }
            .fullScreenCover(isPresented: $isActive, onDismiss: onDismiss) {
                destination
            }
            .onChange(of: selection) { (selection) in
                isActive = selection == tag
            }
        } else {
            Button {
                action()
            } label: {
                label
            }
            .sheet(isPresented: $isActive, onDismiss: onDismiss) {
                destination
            }
            .onChange(of: selection) { (selection) in
                isActive = selection == tag
            }
        }
    }
    
    @ViewBuilder
    func sheetButton(isFullScreen: Bool, action: @escaping (() -> Void)) -> some View {
        if isFullScreen {
            Button {
                action()
            } label: {
                label
            }
            .fullScreenCover(isPresented: $isActiveBinding, onDismiss: onDismiss) {
                destination
            }
        } else {
            Button {
                action()
            } label: {
                label
            }
            .sheet(isPresented: $isActiveBinding, onDismiss: onDismiss) {
                destination
            }
        }
    }
    
    @ViewBuilder
    func sheetButton(isFullScreen: Bool, tag: Int) -> some View {
        if isFullScreen {
            Button {
                selection = tag
            } label: {
                label
            }
            .fullScreenCover(isPresented: $isActive, onDismiss: onDismiss) {
                destination
            }
            .onChange(of: selection) { (selection) in
                isActive = selection == tag
            }
        } else {
            Button {
                selection = tag
            } label: {
                label
            }
            .sheet(isPresented: $isActive, onDismiss: onDismiss) {
                destination
            }
            .onChange(of: selection) { (selection) in
                isActive = selection == tag
            }
        }
    }
    
    @ViewBuilder
    func sheetButton(isFullScreen: Bool) -> some View {
        if isFullScreen {
            Button {
                isActive.toggle()
            } label: {
                label
            }
            .fullScreenCover(isPresented: $isActive, onDismiss: onDismiss) {
                destination
            }
        } else {
            Button {
                isActive.toggle()
            } label: {
                label
            }
            .sheet(isPresented: $isActive, onDismiss: onDismiss) {
                destination
            }
        }
    }
    
    // MARK: -
    
    @ViewBuilder
    func sheetView(isFullScreen: Bool, action: @escaping (() -> Void), tag: Int) -> some View {
        if isFullScreen {
            label.onTapGesture {
                action()
            }
            Button {} label: {
                EmptyView()
            }
            .fullScreenCover(isPresented: $isActive, onDismiss: onDismiss) {
                destination
            }
            .onChange(of: selection) { (selection) in
                isActive = selection == tag
            }
        } else {
            label.onTapGesture {
                action()
            }
            Button {} label: {
                EmptyView()
            }
            .sheet(isPresented: $isActive, onDismiss: onDismiss) {
                destination
            }
            .onChange(of: selection) { (selection) in
                isActive = selection == tag
            }
        }
    }
    
    @ViewBuilder
    func sheetView(isFullScreen: Bool, action: @escaping (() -> Void)) -> some View {
        if isFullScreen {
            label.onTapGesture {
                action()
            }
            Button {} label: {
                EmptyView()
            }
            .fullScreenCover(isPresented: $isActiveBinding, onDismiss: onDismiss) {
                destination
            }
        } else {
            label.onTapGesture {
                action()
            }
            Button {} label: {
                EmptyView()
            }
            .sheet(isPresented: $isActiveBinding, onDismiss: onDismiss) {
                destination
            }
        }
    }
    
    @ViewBuilder
    func sheetView(isFullScreen: Bool, tag: Int) -> some View {
        if isFullScreen {
            label.onTapGesture {
                selection = tag
            }
            Button {} label: {
                EmptyView()
            }
            .fullScreenCover(isPresented: $isActive, onDismiss: onDismiss) {
                destination
            }
            .onChange(of: selection) { (selection) in
                isActive = selection == tag
            }
        } else {
            label.onTapGesture {
                selection = tag
            }
            Button {} label: {
                EmptyView()
            }
            .sheet(isPresented: $isActive, onDismiss: onDismiss) {
                destination
            }
            .onChange(of: selection) { (selection) in
                isActive = selection == tag
            }
        }
    }
    
    @ViewBuilder
    func sheetView(isFullScreen: Bool) -> some View {
        if isFullScreen {
            label.onTapGesture {
                isActive.toggle()
            }
            Button {} label: {
                EmptyView()
            }
            .fullScreenCover(isPresented: $isActive, onDismiss: onDismiss) {
                destination
            }
        } else {
            label.onTapGesture {
                isActive.toggle()
            }
            Button {} label: {
                EmptyView()
            }
            .sheet(isPresented: $isActive, onDismiss: onDismiss) {
                destination
            }
        }
    }
    
    // MARK: -
    
    @ViewBuilder
    func sheetEmptyView(isFullScreen: Bool, tag: Int) -> some View {
        if isFullScreen {
            Button {} label: {
                EmptyView()
            }
            .fullScreenCover(isPresented: $isActive, onDismiss: onDismiss) {
                destination
            }
            .onChange(of: selection) { (selection) in
                isActive = selection == tag
            }
        } else {
            Button {} label: {
                EmptyView()
            }
            .sheet(isPresented: $isActive, onDismiss: onDismiss) {
                destination
            }
            .onChange(of: selection) { (selection) in
                isActive = selection == tag
            }
        }
    }
    
    @ViewBuilder
    func sheetEmptyView(isFullScreen: Bool) -> some View {
        if isFullScreen {
            Button {} label: {
                EmptyView()
            }
            .fullScreenCover(isPresented: $isActiveBinding, onDismiss: onDismiss) {
                destination
            }
        } else {
            Button {} label: {
                EmptyView()
            }
            .sheet(isPresented: $isActiveBinding, onDismiss: onDismiss) {
                destination
            }
        }
    }
    
    // MARK: -
}

public enum NavigationStepStyle {
    case button
    case view
}

public enum NavigationStepType {
    case push
    case sheet
    case fullScreenSheet
}

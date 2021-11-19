//
//  Utiltys.swift
//  TodoListDemo
//
//  Created by walker on 2021/11/17.
//

import SwiftUI
import Combine

// 去重
extension Sequence where Iterator.Element: Hashable {
    func unique() -> [Iterator.Element] {
        var seen: Set<Iterator.Element> = []
        return filter { seen.insert($0).inserted }  // 能插入则代表是新数据
    }
}

// 关闭键盘
extension UIApplication {
    func endEditing() {
        sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
}

// MARK: - Keyboard listener

struct AdaptsToKeyboard: ViewModifier {
    @Binding var keyboardHeight: CGFloat
    
    // 在任一个View出现在屏幕上的时候，注册监听键盘拉起隐藏的事件
    // 再通过Combine的一些方法转成我们感兴趣的height（即键盘高度）
    // 因为我是用的ZStack堆叠的输入框（放置于底部），故在键盘拉起时只需要-=键盘高度就能让输入框贴合在键盘上
    func body(content: Content) -> some View {
        content
            .onAppear(perform: {
                NotificationCenter.Publisher(center: NotificationCenter.default, name: UIResponder.keyboardWillShowNotification)
                    .merge(with: NotificationCenter.Publisher(center: NotificationCenter.default, name: UIResponder.keyboardWillChangeFrameNotification))
                    .compactMap { notification in
                        withAnimation(.easeOut(duration: 1.33)) {
                            // 提取rect
                            notification.userInfo?[UIResponder.keyboardFrameEndUserInfoKey] as? CGRect
                        }
                    }
                    .map { rect in
                        rect.height  // 转成offset需要的值(还未对safearea做优化)
                    }
                    .subscribe(Subscribers.Assign(object: self, keyPath: \.keyboardHeight))
                
                NotificationCenter.Publisher(center: NotificationCenter.default, name: UIResponder.keyboardWillHideNotification)
                    .compactMap { notification in
                        CGFloat.zero
                    }
                    .subscribe(Subscribers.Assign(object: self, keyPath: \.keyboardHeight))
            })
    }
}

extension View {
    func adaptsToKeyboard(offset y: Binding<CGFloat>) -> some View {
        return modifier(AdaptsToKeyboard(keyboardHeight: y))
    }
}


// MARK: - View


// 给View加一个导航控件，增加关闭按钮
// 给sheet用，sheet默认是下拉关闭，不直观，用户添加了组名后并不知道怎样“确认”，我们就给他一个确认按钮
extension View {
    @ViewBuilder
    func wrappedInNavigationViewToMakeDismissable(_ dismiss: (() -> Void)?) -> some View {
        if let dismiss = dismiss {
            // 包一个导航条
            NavigationView {
                self
                    .navigationBarTitleDisplayMode(.inline)
                    .dismissable(dismiss)
            }
            .navigationViewStyle(StackNavigationViewStyle())
        } else {
            self
        }
    }
    
    @ViewBuilder
    func dismissable(_ dismiss: (() -> Void)?) -> some View {
        if let dismiss = dismiss {
            // 给导航条添加一个按钮
            self.toolbar(content: {
                ToolbarItem(placement: .cancellationAction) {
                    Button("确认") { dismiss() }
                }
            })
        } else {
            self
        }
    }
}


// MARK: - PreferenceKeys

struct ScrollOffsetPreferenceKey: PreferenceKey {
    static var defaultValue: CGPoint = .zero
    
    static func reduce(value: inout CGPoint, nextValue: () -> CGPoint) {}
}

// 给ScrollView包了层GeometryReader，
// 以及用KVC把滚动位置广播出去
struct ScrollView<Content: View>: View {
    let axes: Axis.Set
    let showsIndicators: Bool
    let offsetChanged: (CGPoint) -> Void
    let content: Content

    init(
        axes: Axis.Set = .vertical,
        showsIndicators: Bool = true,
        offsetChanged: @escaping (CGPoint) -> Void = { _ in },
        @ViewBuilder content: () -> Content
    ) {
        self.axes = axes
        self.showsIndicators = showsIndicators
        self.offsetChanged = offsetChanged
        self.content = content()
    }
    
    var body: some View {
        SwiftUI.ScrollView(axes, showsIndicators: showsIndicators) {
            GeometryReader { geometry in
                Color.clear.preference(
                    key: ScrollOffsetPreferenceKey.self,
                    value: geometry.frame(in: .named("scrollView")).origin
                )
            }.frame(width: 0, height: 0)
            content
        }
        .coordinateSpace(name: "scrollView")
        .onPreferenceChange(ScrollOffsetPreferenceKey.self, perform: offsetChanged)
    }
}

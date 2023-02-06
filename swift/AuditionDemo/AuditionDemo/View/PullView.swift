//
//  PullView.swift
//  AuditionDemo
//
//  Created by dian bao on 2022/7/28.
//

import SwiftUI

//MARK: - Preferences
struct HeaderBoundsPreferenceKey: PreferenceKey {
    struct Item {
        let bounds: Anchor<CGRect>
    }
    static var defaultValue: [Item] = []
    
    //每次有新的init(bounds)就加入value数组
    static func reduce(value: inout [Item], nextValue: () -> [Item]) {
        value.append(contentsOf: nextValue())
    }
}

//MARK: - Environment
struct HeaderDataKey: EnvironmentKey {
    static var defaultValue: PullRecordData = PullRecordData.init()
}

extension EnvironmentValues {
    var headerData: PullRecordData {
        get { self[HeaderDataKey.self] }
        set { self[HeaderDataKey.self] = newValue }
    }
}

//MARK: - PullRecordData
class PullRecordData:ObservableObject{
    @Published
    var progress: Double = 0                //隐藏加载器的显示透明程度，取决于下拉程度
    var thresold: CGFloat = 0               //下拉阀值即头部高度
    var offset:CGFloat = 0                  //需要增加的偏移量
}

//MARK: - 下拉View
struct PullView<Header, Content> {
    //MARK: - 属性
    private let header: Header
    private let content: () -> Content
    
    @Environment(\.headerData) private var headerData
}

//MARK: - 扩展方法
extension PullView: View where Header: View, Content: View {
    //MARK: - 初始化方法
    init(header: Header, @ViewBuilder content: @escaping () -> Content) {
        self.header = header
        self.content = content
    }
    //MARK: - body
    var body: some View {
        VStack(spacing: 0) {
            header
                .frame(maxWidth: .infinity)
                .anchorPreference(key: HeaderBoundsPreferenceKey.self, value: .bounds, transform: {
                    [.init(bounds: $0)]
                }) //把header的bounds加入已HeaderBoundsPreferenceKey标记的Preference中
            content()
        }
        .padding(.top, -headerData.thresold)
    }
}

//MARK: - Preview
struct PullView_Previews: PreviewProvider {
    static var previews: some View {
        PullView(header: EmptyView(), content: {})
    }
}

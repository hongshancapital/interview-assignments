//
//  IAList.swift
//  InterviewAssignments
//
//  Created by ZhuChaoJun on 2022/6/2.
//

import SwiftUI

struct LoadableList<Content>: View where Content: View {

    private var content: () -> Content
    private var noMoreData: Bool

    //
    //
    init(noMoreData: Binding<Bool>, @ViewBuilder content: @escaping () -> Content) {
        self.noMoreData = noMoreData.wrappedValue
        self.content = content
    }

    var body: some View {

        List {
            content()

            // footer
            Group {
                if noMoreData {
                    Text("No more data.").foregroundColor(.gray)
                } else {
                    GeometryReader { geometry in
                        HStack {
                            Spacer()
                            ProgressView()
                                .padding(.trailing, 5)
                            Text("Loading...")
                                .foregroundColor(.gray)
                            Spacer()
                        }
                        .preference(key: OffsetPreferenceKey.self, value: geometry.frame(in: .global).maxY)
                    }

                }
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
            .listRowInsets(EdgeInsets())
            .background(Color(UIColor.systemGroupedBackground))
        }
        .listStyle(.grouped)
    }
}

extension LoadableList {

    func loadable(action: @escaping () async -> Void) -> some View {
        let loadModify = LoadableModifier(loadable: action)
        return modifier(loadModify)
    }
}

//
//
//
struct LoadableModifier: ViewModifier {
    @State private var canLoad = true

    var loadable: () async -> Void

    func body(content: Content) -> some View {
        content
            .onPreferenceChange(OffsetPreferenceKey.self) { offset in
                if self.canLoad && offset > 0 && offset < UIScreen.main.bounds.height - 34 {
                    Task {
                        self.canLoad = false
                        await loadable()
                        self.canLoad = true
                    }
                }
            }
    }
}

private struct OffsetPreferenceKey: PreferenceKey {
    static var defaultValue: CGFloat = 0

    static func reduce(value: inout CGFloat, nextValue: () -> CGFloat) {
        value = nextValue()
    }
}



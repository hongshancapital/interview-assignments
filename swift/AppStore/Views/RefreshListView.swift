//
//  TestListView.swift
//  AppStore
//
//  Created by huyanling on 2022/5/12.
//

import SwiftUI

struct RefreshListView<Content: View>: View {
    @Binding var hasMore: Bool
    var enableHeaderRefresh: Bool = false
    var enableFooterRefresh: Bool = false
    var footerRefreshAction: (() -> Void)?
    var headerRefreshAction: (() -> Void)?

    var content: () -> Content

    init(@ViewBuilder content: @escaping (() -> Content), refreshAction: (() -> Void)? = nil, footerAction: (() -> Void)? = nil, hasMore: Binding<Bool>) {
        self.content = content
        headerRefreshAction = refreshAction
        enableHeaderRefresh = (refreshAction != nil)

        footerRefreshAction = footerAction
        enableFooterRefresh = (footerAction != nil)
        _hasMore = hasMore
    }

    var body: some View {
        if enableHeaderRefresh {
            List {
                content()
                if enableFooterRefresh {
                    RefreshFooterView(hasMore: $hasMore)
                        .onAppear {
                            footerAction()
                        }
                }
            }.refreshable(action: {
                headerRefreshAction?()
            })
                .environment(\.defaultMinListRowHeight, 0.01)
        } else {
            List {
                content()
                if enableFooterRefresh {
                    RefreshFooterView(hasMore: $hasMore)
                        .onAppear {
                            footerAction()
                        }
                }
            }
        }
    }

    private func footerAction() {
        guard hasMore else { return }
        footerRefreshAction?()
    }
}

struct RefreshFooterView: View {
    @Binding var hasMore: Bool
    var body: some View {
        HStack(alignment: .center) {
            Spacer()
            if hasMore {
                ActivityView()
                Text("Loading...").foregroundColor(.gray)
            } else {
                Text("No more data.").foregroundColor(.gray)
            }
            Spacer()
        }
        .listRowBackground(HSClearBackground())
    }
}

struct ActivityView: UIViewRepresentable {
    func makeUIView(context: UIViewRepresentableContext<ActivityView>) -> UIActivityIndicatorView {
        return UIActivityIndicatorView()
    }

    func updateUIView(_ uiView: UIActivityIndicatorView, context: UIViewRepresentableContext<ActivityView>) {
        uiView.startAnimating()
    }
}

struct HSTableViewPrefernceKey: PreferenceKey {
    static var defaultValue: [CGRect] = [CGRect]()

    static func reduce(value: inout [CGRect], nextValue: () -> [CGRect]) {
        value.append(contentsOf: nextValue())
    }

    typealias Value = [CGRect]
}

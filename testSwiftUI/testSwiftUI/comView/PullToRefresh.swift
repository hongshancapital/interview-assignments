//
//  PullToRefresh.swift
//  testSwiftUI
//
//  Created by pchen on 2023/4/5.
//

import SwiftUI

public enum ListPullStateEnum: Int {
    case none
    case noMoreState
    case isRefreshing
    case isLoadMoreing
}

private struct PullToRefresh: UIViewRepresentable {
    
    @Binding var listPullState: ListPullStateEnum
    
    let onRefresh: () -> Void
    let isLoadMoreStyle: Bool
    
    public init(
        listPullState: Binding<ListPullStateEnum>,
        isLoadMoreStyle: Bool = false,
        onRefresh: @escaping () -> Void
    ) {
        self._listPullState = listPullState
        self.onRefresh = onRefresh
        self.isLoadMoreStyle = isLoadMoreStyle
    }
    
    public class Coordinator {
        let onRefresh: () -> Void
        let listPullState: Binding<ListPullStateEnum>
        
        init(
            onRefresh: @escaping () -> Void,
            listPullState: Binding<ListPullStateEnum>
        ) {
            self.onRefresh = onRefresh
            self.listPullState = listPullState
        }
        
        @objc
        func onValueChanged() {
            print("onValueChange")
            onRefresh()
        }
    }
    
    public func makeUIView(context: UIViewRepresentableContext<PullToRefresh>) -> UIView {
        print("makeUIView")
        let view = UIView(frame: .zero)
        view.isHidden = true
        view.isUserInteractionEnabled = false
        return view
    }
    
    private func tableView(entry: UIView) -> UIScrollView? {
        
        // Search in ancestors
        if let tableView = Introspect.findAncestor(ofType: UIScrollView.self, from: entry) {
            print("findAncestor tableView")
            return tableView
        }
        
        guard let viewHost = Introspect.findViewHost(from: entry) else {
            print("not found tableView")
            return nil
        }
        
        // Search in siblings
        return Introspect.previousSibling(containing: UIScrollView.self, from: viewHost)
    }
    
    public func updateUIView(_ uiView: UIView, context: UIViewRepresentableContext<PullToRefresh>) {
        
        print("updateUIView")
        DispatchQueue.main.asyncAfter(deadline: .now()) {
            
            guard let tableView = self.tableView(entry: uiView) else {
                print("updateUIView tableview null")
                return
            }
            
            if (self.isLoadMoreStyle) {
                self.updateLoadMoreUI(tableView, context: context)
            }else {
                self.updateRefreshUI(tableView, context: context)
            }
        }
    }
    
    func updateLoadMoreUI (_ tableView: UIScrollView, context: UIViewRepresentableContext<PullToRefresh>) {
        
        guard tableView.gtmFooter != nil else {
            tableView.gtm_addLoadMoreFooterView {
                print("excute loadMoreBlock")
                context.coordinator.onValueChanged()
            }
            return
        }
        if self.listPullState == .none {
            tableView.gtmFooter?.endLoadMore(isNoMoreData: false)
        } else if self.listPullState == .noMoreState {
            tableView.gtmFooter?.endLoadMore(isNoMoreData: true)
        }
    }
    
    func updateRefreshUI ( _ tableView: UIScrollView, context: UIViewRepresentableContext<PullToRefresh>) {
        if let refreshControl = tableView.refreshControl {
            if self.listPullState == .isRefreshing {
                print("begin regresh")
                refreshControl.beginRefreshing()
            } else {
                print("end regresh")
                refreshControl.endRefreshing()
            }
            return
        }
        
        let refreshControl = UIRefreshControl()
        refreshControl.addTarget(context.coordinator, action: #selector(Coordinator.onValueChanged), for: .valueChanged)
        tableView.refreshControl = refreshControl
    }
    
    public func makeCoordinator() -> Coordinator {
        print("makeCoordinator")
        return Coordinator(onRefresh: onRefresh, listPullState: $listPullState)
    }
}

extension View {
    
    public func pullToRefresh(listPullState: Binding<ListPullStateEnum>, onRefresh: @escaping () -> Void) -> some View {
        /// listPullState.wrappedValue ? .bottom : .center  to change overLay is to update state
        return overlay(alignment: listPullState.wrappedValue == .isRefreshing ? .bottom : .center, content: {
            PullToRefresh(listPullState: listPullState, onRefresh: onRefresh)
                .frame(width: 0, height: 0)
        })
    }
    
    public func pullUpLoading(listPullState: Binding<ListPullStateEnum>, onLoad: @escaping () -> Void) -> some View {
        /// listPullState.wrappedValue ? .bottom : .center  to change overLay is to update state
        return overlay(alignment: listPullState.wrappedValue == .isRefreshing ? .bottom : .center, content: {
            PullToRefresh(listPullState: listPullState, isLoadMoreStyle: true, onRefresh: onLoad)
                .frame(width: 0, height: 0)
        })
    }
    
}

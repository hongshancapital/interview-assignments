//
//  RefreshHeaderView.swift
//  AppsList
//
//  Created by 余晨正 on 2022/2/15.
//

import SwiftUI

struct RefreshHeaderView: UIViewRepresentable {
    typealias UIViewType = UIView
    
    @Binding private var refreshing: Bool
    private let action: () -> Void
    
    init(refreshing: Binding<Bool>, action: @escaping () -> Void) {
        _refreshing = refreshing
        self.action = action
    }
    
    func makeUIView(context: Context) -> UIView {
        return UIView(frame: CGRect.zero)
    }
    
    func updateUIView(_ uiView: UIView, context: Context) {
        DispatchQueue.main.async {
            guard let rootView = uiView.superview?.superview else {
                return
            }
            guard let tableView = obtainTableView(root: rootView) else {
                return
            }
            if let refreshControl = tableView.refreshControl {
                if self.refreshing {
                    refreshControl.beginRefreshing()
                } else {
                    refreshControl.endRefreshing()
                }
                return
            }
            let refreshControl = UIRefreshControl()
            refreshControl.addTarget(context.coordinator, action: #selector(Coordinator.onValueChanged), for: .valueChanged)
            tableView.refreshControl = refreshControl
        }
    }
    
    func makeCoordinator() -> Coordinator {
        return Coordinator(refreshing: $refreshing, action: action)
    }
}

extension RefreshHeaderView {
    
    class Coordinator {
        let action: () -> Void
        let refreshing: Binding<Bool>
        
        init(refreshing: Binding<Bool>, action: @escaping () -> Void) {
            self.action = action
            self.refreshing = refreshing
        }
        
        @objc
        func onValueChanged() {
            action()
        }
    }
    
}

private extension RefreshHeaderView {
    
    func obtainTableView(root: UIView) -> UITableView? {
        for subview in root.subviews {
            if let tableView = subview as? UITableView {
                return tableView
            } else if let tableView = obtainTableView(root: subview) {
                return tableView
            }
        }
        return nil
    }
}

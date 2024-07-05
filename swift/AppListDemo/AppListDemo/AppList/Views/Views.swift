//
//  Views.swift
//  AppListDemo
//
//  Created by 荣恒 on 2022/2/27.
//

import Foundation
import SwiftUI

// MARK: - AppRefreshHeaderView
struct AppRefreshHeaderView: UIViewRepresentable {
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

extension AppRefreshHeaderView {
    
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

private extension AppRefreshHeaderView {
    
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

// MARK: - AppRefreshFooterView
struct AppRefreshFooterView: View {
    
    @Binding var hasMore: Bool
    @Binding var loading: Bool
        
    init(hasMore: Binding<Bool>, loading: Binding<Bool>) {
        _hasMore = hasMore
        _loading = loading
    }
    
    var body: some View {
        GeometryReader { geo in
            HStack(alignment: .center, spacing: 10) {
                if !hasMore {
                    Text("No more data")
                } else if !loading {
                    Text("load more")
                } else {
                    ProgressView()
                    Text("loading...")
                }
            }
            .frame(width: geo.size.width, height: geo.size.height)
        }
    }
}

// MARK: - AppImageView
struct AppImageView: View {
    
    private var url: String
    
    @State private var logo: UIImage? = nil
    @State private var error: AppImageLoader.Error?
    @State private var loaded: Bool = false
    
    init(url: String) {
        self.url = url
    }
    
    var body: some View {
        if !loaded {
            ProgressView()
                .frame(idealWidth: 50, idealHeight: 50)
                .fixedSize(horizontal: true, vertical: true)
                .onAppear {
                    fetchImage()
                }
        } else {
            Image.init(uiImage: logo ?? (error == nil ? UIImage.init(named: "image-placeholder") ?? UIImage() : UIImage.init(named: "image-error") ?? UIImage()))
                .resizable()
                .frame(idealWidth: 50, idealHeight: 50)
                .fixedSize(horizontal: true, vertical: true)
                .cornerRadius(6)
        }
    }
    
    private func fetchImage () {
        Task.init(priority: TaskPriority.high) {
            loaded = false
            let (image, err) = await AppImageLoader.loadImage(with: url)
            logo = image
            error = err
            loaded = true
        }
    }
}

// MARK: - AppEmptyView
struct AppEmptyView: View {
    private var message: String
    private let action: () -> Void
    @Binding private var refreshing: Bool
    
    init(message: String, refreshing: Binding<Bool>, action: @escaping () -> Void) {
        self.message = message
        _refreshing = refreshing
        self.action = action
    }
    
    var body: some View {
        VStack.init(alignment: .center, spacing: 10) {
            Text(message)
            if refreshing {
                ProgressView()
            } else {
                Button.init("重新加载") {
                    action()
                }
            }
        }
    }
    
}

// MARK: - AppToast
struct AppToast: ViewModifier {
    @Binding var show: Bool
    var title: String
    
    func body(content: Content) -> some View {
        GeometryReader { geo in
            ZStack(){
                content.zIndex(0).disabled(show)
                VStack {
                    HStack {
                        Text(title)
                            .padding(EdgeInsets(top: 20, leading: 30, bottom: 20, trailing: 30))
                            .multilineTextAlignment(.center)
                            .foregroundColor(Color.white)
                    }
                    .background(Color.black.opacity(0.4)).cornerRadius(5)
                    .frame(maxWidth: geo.size.width - 100)
                }
                .frame(width: geo.size.width, height: geo.size.height)
                .background(Color.clear)
                .zIndex(1)
                .opacity((show) ? 1 : 0)
                .animation(.easeInOut(duration: 0.25), value: show)
            }
            .onChange(of: show) { e in
                if(e){
                    DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
                        show.toggle()
                    }
                }
            }
        }
    }
}

extension View {
    
    /// toast 提示
    func toast(show: Binding<Bool>, title: String) -> some View {
        self.modifier(AppToast(show: show, title: title))
    }
}

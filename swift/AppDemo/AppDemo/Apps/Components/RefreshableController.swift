//
//  RefreshableController.swift
//  AppDemo
//
//  Created by jaly on 2022/11/29.
//

import SwiftUI

class RefreshControlController: UIViewController {
    var refreshAction: () async -> Void
    var hostController: UIViewController
    
    init(action: @escaping () async -> Void, hostController: UIViewController) {
        
        self.refreshAction = action
        self.hostController = hostController
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = .clear
        self.addChild(self.hostController)
        self.hostController.view.frame = self.view.bounds
        self.view.addSubview(self.hostController.view)
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        let scrollView = findScrollView(view: self.view)
        let control = UIRefreshControl()
        control.addTarget(self, action: #selector(beginRerefresh(sender: )), for: .valueChanged)
        scrollView?.addSubview(control)
    }
    
    @objc func beginRerefresh(sender: UIRefreshControl) {
        Task {
            await self.refreshAction()
            sender.endRefreshing()
        }
    }
    
    private func findScrollView(view: UIView) -> UIScrollView? {
        if let scrollView = view as? UIScrollView {
            return scrollView
        }
        for sub in view.subviews {
            if let scrollView = findScrollView(view: sub) {
                return scrollView
            }
        }
        return nil
    }
}

struct RefreshableController<Content: View>: UIViewControllerRepresentable {
    let content: Content
    let action: () async -> Void
    
    
    func makeUIViewController(context: Context) -> RefreshControlController {
        let hostingVC = UIHostingController(rootView: self.content)
        let vc = RefreshControlController(action: self.action, hostController: hostingVC)
        return vc
    }
    
    func updateUIViewController(_ uiViewController: RefreshControlController, context: Context) {
        
    }
}

extension View {
    func pullDownToRefresh(action: @escaping @Sendable () async -> Void) -> some View {
        modifier(RefreshaleModifier(action: action))
    }
}

struct RefreshaleModifier: ViewModifier {
    var action: () async -> Void
    
    func body(content: Content) -> some View {
        
        RefreshableController(content: content, action: self.action)
            .background(Color.clear)
            .edgesIgnoringSafeArea(.all)
    }
}

struct RefreshableController_Previews: PreviewProvider {
    static var previews: some View {
        ZStack {
            Color.red.edgesIgnoringSafeArea(.all)
            List{
                ForEach(0...50, id: \.self) { item in
                    Text("Row: \(item)")
                }
            }
            .listStyle(.plain)
            .pullDownToRefresh {
                print("refreshing")
                sleep(2)
                print("complete")
            }
        }
        
    }
}

//

//
//  RefreshUI.swift
//  InterviewDemo
//
//  Created by 霍橙 on 2023/2/2.
//  
//
    

import SwiftUI

extension RefreshUI {
    public enum Style {
        case header, footer
    }
}

//刷新组件
struct RefreshUI: UIViewRepresentable {
    @Binding var isRefreshing: Bool
    let action: (() -> Void)?
    let noMoreText:String?
    private let style: Style
    
    init(style: Style, isRefreshing: Binding<Bool>,
         noMoreText:String? = nil,
         action: (() -> Void)? = nil) {
        self.style = style
        _isRefreshing = isRefreshing
        self.noMoreText = noMoreText
        self.action = action
    }
    
    func makeUIView(context: Context) -> some UIView {
        let uiView = UIView(frame: .zero)
        return uiView
    }
    
    func updateUIView(_ uiView: UIViewType, context: Context) {
        DispatchQueue.main.asyncAfter(deadline: .now()) {
            guard let viewHost = uiView.superview?.superview else {
                return
            }
            guard let scrollView = self.scrollView(root: viewHost) else {
                return
            }
            if style == .header {
                if let refreshControl = scrollView.refreshControl {
                    context.coordinator.resetFooter(scrollView)
                    if self.isRefreshing {
                        refreshControl.beginRefreshing()
                    } else {
                        refreshControl.endRefreshing()
                    }
                    
                }else {
                    let refreshControl = UIRefreshControl()
                    scrollView.refreshControl = refreshControl
                    context.coordinator.setupObserver(scrollView)
                }
            }else if style == .footer {
                context.coordinator.noMoreText = noMoreText
                context.coordinator.setupObserver(scrollView)
            }
            
            
        }
    }
    
    static func dismantleUIView(_ uiView: UIView, coordinator: Coordinator) {
        coordinator.clearObserver()
    }
    
}

extension RefreshUI {
    func makeCoordinator() -> Coordinator {
        return Coordinator(style, isRefreshing: $isRefreshing,
                           noMoreText:noMoreText,
                           action:action)
    }
    
    class Coordinator {
        let style: Style
        var isRefreshing: Binding<Bool>
        var noMoreText:String?
        let action: (() -> Void)?
        private var offsetToken: NSKeyValueObservation?
        private var stateToken: NSKeyValueObservation?
        private var sizeToken: NSKeyValueObservation?
        private var initOffset:CGFloat = 0
        private var initInsetBottom:CGFloat = 0
        private let height:CGFloat = 60
        private var progress: CGFloat = 0
        private var footerView:FooterView?
        
        init(_ style:Style, isRefreshing: Binding<Bool>,
             noMoreText:String?,
             action: (() -> Void)?) {
            self.style = style
            self.isRefreshing = isRefreshing
            self.noMoreText = noMoreText
            self.action = action
        }
        
    }
    
    private func scrollView(root: UIView) -> UIScrollView? {
        for subview in root.subviews {
            if subview.isKind(of: UIScrollView.self) {
                return subview as? UIScrollView
            } else if let scrollView = scrollView(root: subview) {
                return scrollView
            }
        }
        return nil
    }
    
}

private extension RefreshUI.Coordinator {
    func resetFooter(_ scrollView: UIScrollView) {
        if style == .header {
            if initInsetBottom != scrollView.contentInset.bottom, scrollView.contentOffset.y <= initOffset - height {
                scrollView.contentInset.bottom = initInsetBottom
            }
        }
        
    }
    
    func setupFooterView(_ scrollView: UIScrollView) {
        if style == .footer{
            if footerView == nil  {
                footerView = FooterView(frame:.zero)
            }
            footerView?.loadingText = noMoreText
            if noMoreText != nil && !isRefreshing.wrappedValue {
                scrollView.contentInset.bottom = initInsetBottom + self.height
            }
            
            if footerView?.isRefreshing != isRefreshing.wrappedValue && !isRefreshing.wrappedValue {
                DispatchQueue.main.async {
                    UIView.animate(withDuration: 0.3, animations: {
                        if self.noMoreText == nil {
                            scrollView.contentInset.bottom -= self.height
                        }
                        
                    }, completion: { _ in
                        
                        self.progress = 0
                        
                    })
                }
                
            }
            //isRefreshing 状态发生变化则重新赋值
            footerView?.isRefreshing = isRefreshing.wrappedValue
            
        }
        
    }

    func setupObserver(_ scrollView: UIScrollView) {
        
        setupFooterView(scrollView)
        
        if (offsetToken != nil) {
            return;
        }
        offsetToken = scrollView.observe(\.contentOffset) { [weak self] scrollView, _ in
            self?.scrollViewDidScroll(scrollView)
        }
        
        stateToken = scrollView.observe(\.panGestureRecognizer.state) {
            [weak self] scrollView,_  in
            
            guard scrollView.panGestureRecognizer.state == .ended else { return }
            
            self?.scrollViewDidEndDragging(scrollView)
        }
        
        initInsetBottom = scrollView.contentInset.bottom
        
        if style == .header {
            initOffset = scrollView.contentOffset.y
            
        }else {
            guard let footerView = footerView  else {
                return
            }
            scrollView.insertSubview(footerView, at: 0)
            sizeToken = scrollView.observe(\.contentSize) { [weak self] scrollView, _ in
                footerView.frame = CGRect(x: 0, y: scrollView.contentSize.height, width: UIScreen.main.bounds.width, height: self?.height ?? 0)
                footerView.isHidden = scrollView.contentSize.height <= scrollView.bounds.height && self?.noMoreText == nil
                
            }
        }
        
    }
    func clearObserver() {
        offsetToken?.invalidate()
        stateToken?.invalidate()
        sizeToken?.invalidate()
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if isRefreshing.wrappedValue { return }
        if style == .footer {
            if scrollView.contentSize.height > scrollView.bounds.height {
                progress = min(1, max(0, (scrollView.contentOffset.y + scrollView.bounds.height - scrollView.contentSize.height - scrollView.contentInsetBottom) / height))
            }
           
        }
        
    }
    
    func scrollViewDidEndDragging(_ scrollView: UIScrollView) {
        if isRefreshing.wrappedValue { return }
        
        switch style {
        case .header:
            if initOffset - scrollView.contentOffset.y < 40 {
                return
            }
        case .footer:
            if progress < 1 { return }
            if noMoreText != nil { return }
            progress = 1
            scrollView.contentInset.bottom += self.height
        }
        
        isRefreshing.wrappedValue = true
        if let actionMethod = action {
            actionMethod()
        }
        
    }
    
}

private extension UIScrollView {
    var contentInsetTop: CGFloat {
        if #available(iOS 11.0, *) {
            return contentInset.top + adjustedContentInset.top
        } else {
            return contentInset.top
        }
    }

    var contentInsetBottom: CGFloat {
        if #available(iOS 11.0, *) {
            return contentInset.bottom + adjustedContentInset.bottom
        } else {
            return contentInset.bottom
        }
    }
}

private class FooterView: UIView {
    var isRefreshing = false {
        didSet {
            if isRefreshing {
                indicator.startAnimating()
                label.isHidden = isRefreshing
            }else {
                indicator.stopAnimating()
                label.isHidden = loadingText == nil
            }
        }
    }
    
    let indicator = UIActivityIndicatorView(style: .medium)
    var loadingText: String? = nil {
        didSet {
            
            label.text = loadingText
            label.sizeToFit()
        }
    }

    private lazy var label: UILabel = {
        let label = UILabel()
        label.font = UIFont.systemFont(ofSize: 14)
        label.textColor = UIColor.black.withAlphaComponent(0.8)

        return label
    }()

    override init(frame: CGRect) {
        super.init(frame: frame)
        addSubview(indicator)
        addSubview(label)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func layoutSubviews() {
        super.layoutSubviews()
        label.center = CGPoint(x: bounds.midX, y: bounds.midY)
        indicator.center = CGPoint(x: bounds.midX, y: bounds.midY)
    }

}


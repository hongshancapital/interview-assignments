//
//  PullRefresg.swift
//  Demo
//
//  Created by mac on 2022/7/26.
//

import SwiftUI

struct PullRefresh:UIViewRepresentable{

    
    @Binding var isRefreshing: Bool
    
    let refresh:((PullRefresh)->Void)?
    
    let loadMore:((PullRefresh)->Void)?
    
    let label:UILabel = UILabel(frame: CGRect(x: 0, y: 0, width: 200, height: 30))
    
    @State var noMoreData:Bool = false
    
    let refreshControl:UIRefreshControl = UIRefreshControl()
    
    init(isRefreshing: Binding<Bool>,refresh: ((PullRefresh) -> Void)? = nil,loadMore:((PullRefresh) -> Void)? = nil) {
        _isRefreshing = isRefreshing
        self.refresh = refresh
        self.loadMore = loadMore
    }
    
    
    func makeUIView(context: Context) -> some UIView {
        let view = UIView(frame: .zero)
        return view
    }
    
    func updateUIView(_ uiView: UIViewType, context: Context) {
        DispatchQueue.main.asyncAfter(deadline: .now()) {
            guard let viewHost = uiView.superview?.superview else{
                return
            }
            guard let tableView = self.tableView(root: viewHost) else {
                return
            }
            if let  control = tableView.refreshControl {
                if self.isRefreshing {
                    control.beginRefreshing()
                } else {
                    control.endRefreshing()
                }
            }else {
                tableView.refreshControl = refreshControl
                tableView.refreshControl?.addTarget(context.coordinator, action: #selector(Coordinator.onValueChanged), for: .valueChanged)
                let tableFooterView = UIView(frame: CGRect(x: 0, y: 0, width:tableView.frame.size.width, height: 30))
                label.textAlignment = .center
                label.textColor = .gray
                label.font = UIFont.systemFont(ofSize: 14)
                label.center = tableFooterView.center
                label.text =  "loading more ···" 
                tableFooterView.addSubview(label)
                tableView.tableFooterView = tableFooterView
                context.coordinator.setupObserver(tableView)
            }
        }
    }
    func makeCoordinator() -> Coordinator {
        return Coordinator($isRefreshing, refresh: refresh,loadMore: loadMore,pullRefresh: self,noMoreData: $noMoreData)
    }
    
     func endWithNoMoreData(){
         self.isRefreshing = false
        noMoreData = true
        label.text = "no more data";
    }
        
    class Coordinator {
        @Binding var isRefreshing: Bool
        let refresh: ((PullRefresh) -> Void)?
        let loadMore:((PullRefresh)->Void)?
        @Binding var noMoreData:Bool
        private var stateToken: NSKeyValueObservation?
        let pullRefresh:PullRefresh
 
        
        init(_ isRefreshing: Binding<Bool> ,refresh: ((PullRefresh) -> Void)?,loadMore:((PullRefresh) -> Void)?,pullRefresh:PullRefresh, noMoreData:Binding<Bool>) {
            _isRefreshing = isRefreshing
            self.refresh = refresh
            self.loadMore = loadMore
            self.pullRefresh = pullRefresh
            _noMoreData = noMoreData
        }

        @objc
        func onValueChanged() {
            self.isRefreshing = true
            if let actionMethod = refresh {
                actionMethod(pullRefresh)
            }
        }
        
        func  setupObserver(_ scrollView:UIScrollView){
            stateToken = scrollView.observe(\.contentOffset, changeHandler: { scr, _ in
                if scr.frame.size.height+scr.contentOffset.y > scr.contentSize.height && self.isRefreshing == false&&scr.contentSize.height>scr.frame.size.height && self.noMoreData == false {
                    self.isRefreshing = true
                    if let loadMoreAction = self.loadMore {
                        loadMoreAction(self.pullRefresh)
                    }
                }
            })
        }
    }
    
    func tableView(root:UIView)->UITableView?{
        for subview in root.subviews {
            if subview.isKind(of: UITableView.self) {
                return subview as? UITableView
            }else if let tableView = tableView(root: subview){
                return tableView
            }
        }
        return nil
    }
    
   
    
}


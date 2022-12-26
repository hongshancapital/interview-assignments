//
//  ViewController.swift
//  XCWorks
//
//  Created by 张兴程 on 2022/12/26.
//

import UIKit
import MJRefresh

class ViewController: UIViewController {

    /// （0）tableView
    var xcTableView : UITableView!
    /// （1）网络请求管理类
    var xcRequestManager : XCRequestManager!
    /// （2）数据处理类
    var xcDataSourceManager : XCDataSourceManager!
    /// （3）表格工厂
    var xcTableViewCellFectory : XCTableViewCellFectory!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        view.backgroundColor = UIColor(red: 244/255, green: 243/255, blue: 247/255, alpha: 0.5)
        self.navigationController?.navigationBar.barTintColor = UIColor.white
        self.navigationItem.title = "App"
        
        createCellFectory()
        createTableView()
        // 首页数据管理类
        createDataSourceManager()
        // 首页请求
        createRequestManager()
    }
    
    /// 创建请求类
    func createRequestManager() {
        xcRequestManager = XCRequestManager.init()
        xcRequestManager.feedListRequest()
        weak var weakSelf = self
        xcRequestManager.reslutBlock = { (result) -> Void in
            weakSelf?.xcDataSourceManager.processData(result: result as! NSDictionary)
        }
    }
    
    /// 创建数据管理类
    func createDataSourceManager() {
        xcDataSourceManager = XCDataSourceManager.init()
        weak var weakSelf = self
        xcDataSourceManager.processFinishBlock = { () -> Void in
            weakSelf?.xcTableViewCellFectory.xcDataSourceManager = weakSelf?.xcDataSourceManager
            weakSelf?.xcTableView.reloadData()
        }
    }
    
    func createCellFectory() {
        xcTableViewCellFectory = XCTableViewCellFectory.init()
        weak var weakSelf = self
        xcTableViewCellFectory.refreshTableViewBlock = { () -> Void in
            weakSelf?.xcTableView.reloadData()
        }
        
        xcTableViewCellFectory.xcScrollViewDidScrollBlock = { (scrollView) -> Void in
            let offsetY = scrollView.contentOffset.y
            let alpha = 1 - (64 - offsetY) / 64
            weakSelf?.navigationController?.navigationBar.alpha = alpha
        }
    }
    
    /// 创建tableview
    func createTableView(){
        xcTableView = UITableView.init(frame: self.view.bounds, style: .plain)
        xcTableView.backgroundColor = UIColor(red: 244/255, green: 243/255, blue: 247/255, alpha: 0.5)
        xcTableView.delegate = xcTableViewCellFectory
        xcTableView.dataSource = xcTableViewCellFectory
        xcTableView.separatorStyle = UITableViewCell.SeparatorStyle.none
        self.xcTableView!.register(XCHomePageFeedCell.self, forCellReuseIdentifier: XC_HOMEPAGE_FEED_IDENTIFIER)
        self.xcTableView!.register(XCFirstFloorCell.self, forCellReuseIdentifier: XC_FIRST_FLOOR_IDENTIFIER)
        view.addSubview(xcTableView)
        
        xcTableView.mas_makeConstraints { (make) in
            make?.top.equalTo()(self.view.mas_top)?.offset()(CGFloat(-84))
            make?.left.mas_equalTo()(0)
            make?.bottom.mas_equalTo()(self.view.mas_bottom)
            make?.right.mas_equalTo()(0)
        }
        
        xcTableView.mj_header = MJRefreshNormalHeader() {
            // 下拉刷新
            self.xcRequestManager.feedListLoadRefreshRequest()
            weak var weakSelf = self
            self.xcRequestManager.reslutBlock = { (result) -> Void in
                weakSelf?.xcDataSourceManager.refreshData(result: result as! NSDictionary)
                // 停止刷新
                weakSelf?.xcTableView.mj_header?.endRefreshing()
            }
        }
        
        xcTableView.mj_footer = MJRefreshBackNormalFooter() {
            // 上拉加载更多
            self.xcRequestManager.feedListRequest()
            weak var weakSelf = self
            self.xcRequestManager.reslutBlock = { (result) -> Void in
                weakSelf?.xcDataSourceManager.processData(result: result as! NSDictionary)
                // 停止刷新
                weakSelf?.xcTableView.mj_footer?.endRefreshing()
            }
        }
    }


}


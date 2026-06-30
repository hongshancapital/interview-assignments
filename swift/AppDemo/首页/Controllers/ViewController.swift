//
//  ViewController.swift
//  AppDemo
//
//  Created by 操喜平 on 2022/8/10.
//

import UIKit
import Kingfisher
import RxSwift
import RxCocoa
import MJRefresh

fileprivate let pageSize = 10
let headerSize = 120

class ViewController: UIViewController {
    var dataSource: HomeViewDataSource!
    @IBOutlet weak var activityView: UIActivityIndicatorView!
    @IBOutlet weak var statusView: UIView!
    @IBOutlet weak var tableView: UITableView!
    
    @IBOutlet weak var topView: UIView!
    var noMoreDataLab: UILabel!
    let disposeBag =   DisposeBag()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupUI()
        self.tableView.rx.didScroll
            .subscribe { _ in
                if self.tableView.contentOffset.y < 50 {
                    let  percent: Float = Float((self.tableView.contentOffset.y+20) / 70.0)
                    self.topView.alpha = CGFloat(percent)
                    self.statusView.alpha = CGFloat(percent)
                }else{
                    self.topView.alpha = 1
                    self.statusView.alpha = 1
                }
               
            }.disposed(by: disposeBag)

       
    }
    
    
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        netWorkRequest(pageIndex: 0,animated: true,timeout: 800)

    }
    
    func netWorkRequest(pageIndex:Int,animated:Bool,timeout:Int){
      
        if animated {self.activityView.startAnimating()}
        
        let viewModel : HomeViewModel = HomeViewModel()
        viewModel.simulationRequest(pageIndex: pageIndex, page: pageSize, timeOut: timeout) { arr in
            if animated {self.activityView.stopAnimating()}
            self.dataArr.addObjects(from: arr as! [Any])
            self.tableView.reloadData()
            if pageIndex != 0 {
                if arr.count == 0 {
                    self.tableView.mj_footer?.endRefreshingWithNoMoreData()
                }else{
                    self.tableView.mj_footer?.endRefreshing()
                }
            }else{
                self.tableView.mj_footer?.resetNoMoreData()
            }
        } mistake: { err in
            print("网络请求失败")
        }

    }
    
    func setupUI() {
        
        self.tableView.backgroundColor = UIColor.init(hexStr: "ebebeb")
        self.dataSource = HomeViewDataSource.init(dataArr: dataArr, register: { registerId in
                self.tableView.register(UINib.init(nibName: "HomeViewCell", bundle: Bundle.main), forCellReuseIdentifier: registerId)
        }, configCell: { cell, model, indexPath in
            cell.titleLab.text = model.artistName
            cell.subTitleLab.text = model.descriptionStr
            cell.iconView.kf.indicatorType = .activity
            cell.iconBtn.isSelected = model.selected
            if let url = URL(string:model.artworkUrl512!) {
                cell.iconView.kf.indicator?.startAnimatingView()
                cell.iconView.kf.setImage(with: url) {
                    result in
                    cell.iconView.kf.indicator?.stopAnimatingView()
                }
            }
            cell.btnClickBlock = {selected in
                model.selected = selected
            }
         
        },tableView: self.tableView)
        let view :UIView = UIView.init(frame: CGRect(x: 0, y: 0, width: Int(UIScreen.main.bounds.size.width), height: headerSize))
        let lab :UILabel = UILabel.init(frame: CGRect(x: 16, y: 50, width: 80, height: 60))
        lab.text = "APP"
        lab.font = UIFont.boldSystemFont(ofSize: 40)
        view.addSubview(lab)
        view.backgroundColor = UIColor.init(hexStr: "ebebeb")
        self.tableView.separatorStyle = .singleLine
        self.tableView.separatorColor = UIColor.clear
        self.tableView.tableHeaderView = view
        self.tableView.dataSource = self.dataSource
        self.tableView.delegate = self.dataSource
        self.tableView.addSubview(self.refreshCtr)
        
        let mjRefresh: MJRefreshAutoNormalFooter = MJRefreshAutoNormalFooter(){
            self.loadMore()
        }
        mjRefresh.setTitle("Load...", for: .refreshing)
        mjRefresh.setTitle("No more data.", for: .noMoreData)
        mjRefresh.setTitle("", for: .idle)
        self.tableView.mj_footer = mjRefresh
      
    }
    
    lazy var dataArr: NSMutableArray = {
    
        dataArr = NSMutableArray()
        
        return dataArr
    }()
    //下拉刷新
    @objc func refreshHead() {
        self.refreshCtr.endRefreshing()
        self.dataArr .removeAllObjects()
        self.netWorkRequest(pageIndex: 0,animated:false,timeout: 400)
        
       
        
    }
    
    //上拉加载更多
    @objc func loadMore() {
        if self.refreshCtr.isRefreshing {
            self.refreshCtr.endRefreshing()
        }
        let newPageIndex = self.dataArr.count/pageSize
        self.netWorkRequest(pageIndex: newPageIndex,animated:false,timeout: 800)
      
        
    }
    
    
    
    
    lazy var refreshCtr: UIRefreshControl = {
        let refreshCtr = UIRefreshControl()
        refreshCtr.addTarget(self, action: #selector(refreshHead), for: .valueChanged)
        return refreshCtr
    }()
}

